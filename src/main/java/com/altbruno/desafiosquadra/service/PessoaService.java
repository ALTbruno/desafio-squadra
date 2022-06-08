package com.altbruno.desafiosquadra.service;

import com.altbruno.desafiosquadra.dto.*;
import com.altbruno.desafiosquadra.exception.NegocioException;
import com.altbruno.desafiosquadra.model.*;
import com.altbruno.desafiosquadra.repository.BairroRepository;
import com.altbruno.desafiosquadra.repository.EnderecoRepository;
import com.altbruno.desafiosquadra.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BairroRepository bairroRepository;

	@Transactional
	public List<PessoaDtoResumido> cadastrar(PessoaDtoPost pessoaDtoPost) {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(pessoaDtoPost.getNome().toUpperCase());
		pessoa.setSobrenome(pessoaDtoPost.getSobrenome().toUpperCase());
		pessoa.setIdade(pessoaDtoPost.getIdade());
		validaLogin(pessoaDtoPost.getLogin());
		pessoa.setLogin(pessoaDtoPost.getLogin());
		pessoa.setSenha(pessoaDtoPost.getSenha());
		validaStatus(pessoaDtoPost.getStatus());
		pessoa.setStatus(pessoaDtoPost.getStatus());
		pessoaRepository.save(pessoa);

		List<EnderecoDtoPost> enderecoDtoPost = pessoaDtoPost.getEnderecos();
		List<Endereco> enderecos = converterDtoPostEmEnderecos(enderecoDtoPost, pessoa);
		enderecoRepository.saveAll(enderecos);

		pessoa.setEnderecos(enderecos);
		pessoaRepository.save(pessoa);

		return listarPessoasResumido();
	}

	public List<PessoaDtoResumido> listarPessoasResumido() {
		List<Pessoa> pessoas = pessoaRepository.findAllByOrderByCodigoPessoaDesc();
		return converterEmPessoaDtoResumido(pessoas);
	}

	public PessoaDtoBuscaPorCodigoPessoa buscaPorCodigoPessoa(Integer codigoPessoa) {
		Pessoa pessoa = buscarPorCodigoPessoa(codigoPessoa);
		List<Endereco> enderecosPessoa = pessoa.getEnderecos();

		PessoaDtoBuscaPorCodigoPessoa pessoaCompleta = new PessoaDtoBuscaPorCodigoPessoa();
		pessoaCompleta.setCodigoPessoa(pessoa.getCodigoPessoa());
		pessoaCompleta.setNome(pessoa.getNome());
		pessoaCompleta.setSobrenome(pessoa.getSobrenome());
		pessoaCompleta.setIdade(pessoa.getIdade());
		pessoaCompleta.setLogin(pessoa.getLogin());
		pessoaCompleta.setSenha(pessoa.getSenha());
		pessoaCompleta.setStatus(pessoa.getStatus());
		pessoaCompleta.setEnderecos(converterEnderecosEmDto(enderecosPessoa));
		return pessoaCompleta;
	}

	@Transactional
	public List<PessoaDtoResumido> atualizar(PessoaDtoPut pessoaDto) {
		Pessoa pessoaSalvaNoBanco = buscarPorCodigoPessoa(pessoaDto.getCodigoPessoa());
		if (!pessoaDto.getLogin().equalsIgnoreCase(pessoaSalvaNoBanco.getLogin())) validaLogin(pessoaDto.getLogin());
		validaStatus(pessoaDto.getStatus());
		pessoaSalvaNoBanco.setNome(pessoaDto.getNome());
		pessoaSalvaNoBanco.setSobrenome(pessoaDto.getSobrenome());
		pessoaSalvaNoBanco.setIdade(pessoaDto.getIdade());
		pessoaSalvaNoBanco.setLogin(pessoaDto.getLogin());
		pessoaSalvaNoBanco.setSenha(pessoaDto.getSenha());
		pessoaSalvaNoBanco.setStatus(pessoaDto.getStatus());

		List<Endereco> enderecosBody = converterDtoPutEmEnderecos(pessoaDto.getEnderecos());
		enderecoRepository.saveAll(enderecosBody);

		for (Endereco endereco : pessoaSalvaNoBanco.getEnderecos()) {
			for (Endereco enderecoBody : enderecosBody) {
				if (!endereco.getCodigoEndereco().equals(enderecoBody.getCodigoEndereco())){
					enderecoRepository.deleteByCodigoEndereco(endereco.getCodigoEndereco());
				};
			}
		}

		pessoaSalvaNoBanco.setEnderecos(enderecosBody);
		pessoaRepository.save(pessoaSalvaNoBanco);

		return listarPessoasResumido();
	}

	public List<PessoaDtoResumido> inativar(Integer codigoPessoa) {
		Pessoa pessoa = buscarPorCodigoPessoa(codigoPessoa);
		pessoa.setStatus(Status.INATIVO.getId());
		pessoaRepository.save(pessoa);
		return listarPessoasResumido();
	}

	public Pessoa buscarPorCodigoPessoa(Integer codigoPessoa) {
		return pessoaRepository.findByCodigoPessoa(codigoPessoa).orElseThrow(
				() -> new EntityNotFoundException("Código Pessoa não encontrado: " + codigoPessoa));
	}

	public List<Endereco> converterDtoPostEmEnderecos(List<EnderecoDtoPost> enderecosDtoPost, Pessoa pessoa){
		return enderecosDtoPost
				.stream()
				.map( enderecoDtoPost -> {
					Bairro bairro = buscarPorCodigoBairro(enderecoDtoPost.getCodigoBairro());
					String nomeRua = enderecoDtoPost.getNomeRua().toUpperCase();
					String numero = enderecoDtoPost.getNumero().toUpperCase();
					String complemento = enderecoDtoPost.getComplemento().toUpperCase();
					String cep = enderecoDtoPost.getCep();

					Endereco endereco = new Endereco();
					endereco.setBairro(bairro);
					endereco.setNomeRua(nomeRua);
					endereco.setNumero(numero);
					endereco.setComplemento(complemento);
					endereco.setCep(cep);
					endereco.setPessoa(pessoa);
					return endereco;
				}).collect(Collectors.toList());
	}

	public List<Endereco> converterDtoPutEmEnderecos(List<EnderecoDtoPut> enderecosDtoPut){
		return enderecosDtoPut
				.stream()
				.map( enderecoDtoPut -> {
					if (enderecoDtoPut.getCodigoEndereco() == null) {
						Endereco endereco = new Endereco();
						endereco.setNomeRua(enderecoDtoPut.getNomeRua());
						endereco.setNumero(enderecoDtoPut.getNumero());
						endereco.setComplemento(enderecoDtoPut.getComplemento());
						endereco.setCep(enderecoDtoPut.getCep());
						endereco.setBairro(buscarPorCodigoBairro(enderecoDtoPut.getCodigoBairro()));
						endereco.setPessoa(buscarPorCodigoPessoa(enderecoDtoPut.getCodigoPessoa()));
						return enderecoRepository.save(endereco);
					}

					Endereco endereco =  buscarPorCodigoEndereco(enderecoDtoPut.getCodigoEndereco());
					endereco.setNomeRua(enderecoDtoPut.getNomeRua());
					endereco.setNumero(enderecoDtoPut.getNumero());
					endereco.setComplemento(enderecoDtoPut.getComplemento());
					endereco.setCep(enderecoDtoPut.getCep());
					endereco.setBairro(buscarPorCodigoBairro(enderecoDtoPut.getCodigoBairro()));
					endereco.setPessoa(buscarPorCodigoPessoa(enderecoDtoPut.getCodigoPessoa()));
					return endereco;
				}).collect(Collectors.toList());
	}

	public List<EnderecoDtoBuscaPorCodigoPessoa> converterEnderecosEmDto(List<Endereco> enderecos){
		return enderecos
				.stream()
				.map( endereco -> {
					Integer codigoEndereco = endereco.getCodigoEndereco();
					Integer codigoBairro = endereco.getBairro().getCodigoBairro();
					Integer codigoPessoa = endereco.getPessoa().getCodigoPessoa();
					String nomeRua = endereco.getNomeRua();
					String numero = endereco.getNumero();
					String complemento = endereco.getComplemento();
					String cep = endereco.getCep();

					EnderecoDtoBuscaPorCodigoPessoa enderecoDto = new EnderecoDtoBuscaPorCodigoPessoa();
					enderecoDto.setCodigoEndereco(codigoEndereco);
					enderecoDto.setCodigoBairro(codigoBairro);
					enderecoDto.setCodigoPessoa(codigoPessoa);
					enderecoDto.setNomeRua(nomeRua);
					enderecoDto.setNumero(numero);
					enderecoDto.setComplemento(complemento);
					enderecoDto.setCep(cep);
					enderecoDto.setBairroDtoBuscaPorCodigoPessoa(converterBairroEmDto(endereco.getBairro()));
					return enderecoDto;
				}).collect(Collectors.toList());
	}

	public BairroDtoBuscaPorCodigoPessoa converterBairroEmDto(Bairro bairro){
		BairroDtoBuscaPorCodigoPessoa bairroDto = new BairroDtoBuscaPorCodigoPessoa();
		bairroDto.setCodigoBairro(bairro.getCodigoBairro());
		bairroDto.setCodigoMunicipio(bairro.getMunicipio().getCodigoMunicipio());
		bairroDto.setNome(bairro.getNome());
		bairroDto.setStatus(bairroDto.getStatus());
		bairroDto.setMunicipioDtoBuscaPorCodigoPessoa(converterMunicipioEmDto(bairro.getMunicipio()));
		return bairroDto;
	}

	public MunicipioDtoBuscaPorCodigoPessoa converterMunicipioEmDto(Municipio municipio) {
		MunicipioDtoBuscaPorCodigoPessoa municipioDto = new MunicipioDtoBuscaPorCodigoPessoa();
		municipioDto.setCodigoMunicipio(municipio.getCodigoMunicipio());
		municipioDto.setCodigoUF(municipio.getUf().getCodigoUF());
		municipioDto.setNome(municipio.getNome());
		municipioDto.setStatus(municipio.getStatus());
		municipioDto.setUf(municipio.getUf());
		return municipioDto;
	}

	public List<PessoaDtoResumido> converterEmPessoaDtoResumido(List<Pessoa> pessoas){
		return pessoas
				.stream()
				.map( pessoa -> {
					Integer codigoPessoa = pessoa.getCodigoPessoa();
					String nome = pessoa.getNome().toUpperCase();
					String sobrenome = pessoa.getSobrenome().toUpperCase();
					Integer idade = pessoa.getIdade();
					String login = pessoa.getLogin();
					String senha = pessoa.getSenha();
					Integer status = pessoa.getStatus();

					PessoaDtoResumido pessoaDtoResumido = new PessoaDtoResumido();
					pessoaDtoResumido.setCodigoPessoa(codigoPessoa);
					pessoaDtoResumido.setNome(nome);
					pessoaDtoResumido.setSobrenome(sobrenome);
					pessoaDtoResumido.setIdade(idade);
					pessoaDtoResumido.setLogin(login);
					pessoaDtoResumido.setSenha(senha);
					pessoaDtoResumido.setStatus(status);
					return pessoaDtoResumido;
				}).collect(Collectors.toList());
	}

	public Bairro buscarPorCodigoBairro(Integer codigoBairro) {
		return bairroRepository.findByCodigoBairro(codigoBairro).orElseThrow(
				() -> new EntityNotFoundException("Código Bairro não encontrado: " + codigoBairro));
	}

	public Endereco buscarPorCodigoEndereco(Integer codigoEndereco) {
		return enderecoRepository.findByCodigoEndereco(codigoEndereco).orElseThrow(
				() -> new EntityNotFoundException("Código Endereço não encontrado: " + codigoEndereco));
	}

	public void validaStatus(Integer status) {
		if (status != 1 && status != 2) throw new NegocioException("Status inválido. O status deve ser 1 ou 2");
	}

	public void validaLogin(String login) {
		if (pessoaRepository.existsByLogin(login)) throw new NegocioException("Já existe uma pessoa cadastrada como o login " + login);
	}
}
