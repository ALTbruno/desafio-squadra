package com.altbruno.desafiosquadra.service;

import com.altbruno.desafiosquadra.dto.get.BairroDtoGet;
import com.altbruno.desafiosquadra.dto.post.BairroDtoPost;
import com.altbruno.desafiosquadra.exception.NegocioException;
import com.altbruno.desafiosquadra.model.Bairro;
import com.altbruno.desafiosquadra.model.Municipio;
import com.altbruno.desafiosquadra.model.Status;
import com.altbruno.desafiosquadra.repository.BairroRepository;
import com.altbruno.desafiosquadra.repository.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BairroService {

	@Autowired
	private BairroRepository bairroRepository;

	@Autowired
	private MunicipioRepository municipioRepository;

	public List<BairroDtoGet> cadastrar(BairroDtoPost bairroDtoPost) {
		String nome = bairroDtoPost.getNome().toUpperCase()	;
		Integer status = bairroDtoPost.getStatus();
		Municipio municipio = buscarPorCodigoMunicipio(bairroDtoPost.getCodigoMunicipio());

		validaStatus(status);
		validaNomePorMunicipio(nome, municipio);

		Bairro bairro = new Bairro();
		bairro.setNome(nome);
		bairro.setStatus(status);
		bairro.setMunicipio(municipio);
		bairroRepository.save(bairro);
		return listar();
	}

	public List<BairroDtoGet> listar() {
		List<Bairro> bairros = bairroRepository.findAllByOrderByCodigoBairroDesc();
		return converterBairros(bairros);
	}

	public List<BairroDtoGet> listar(Example<BairroDtoGet> example) {
		BairroDtoGet bairroDtoGet = example.getProbe();

		Bairro bairro = new Bairro();
		bairro.setCodigoBairro(bairroDtoGet.getCodigoBairro());
		bairro.setNome(bairroDtoGet.getNome());
		bairro.setStatus(bairroDtoGet.getStatus());
		Boolean codigoMunicipioValido = municipioRepository.existsByCodigoMunicipio(bairroDtoGet.getCodigoMunicipio());
		if (bairroDtoGet.getCodigoMunicipio() != null && codigoMunicipioValido) bairro.setMunicipio(buscarPorCodigoMunicipio(bairroDtoGet.getCodigoMunicipio()));

		if (bairroDtoGet.getCodigoMunicipio() != null && !codigoMunicipioValido) {
			return new ArrayList<>();
		}

		Example<Bairro> bairroExample = Example.of(bairro, example.getMatcher());
		List<Bairro> bairros = bairroRepository.findAll(bairroExample);
		Collections.sort(bairros);
		return converterBairros(bairros);
	}

	public List<BairroDtoGet> atualizar(BairroDtoGet bairroDtoGet) {
		Bairro bairroSalvoNoBanco = buscarPorCodigoBairro(bairroDtoGet.getCodigoBairro());

		String nome = bairroDtoGet.getNome().toUpperCase();
		Integer status = bairroDtoGet.getStatus();
		Integer codigoMunicipio = bairroDtoGet.getCodigoMunicipio();
		Municipio municipio = buscarPorCodigoMunicipio(codigoMunicipio);

		if (!nome.equalsIgnoreCase(bairroSalvoNoBanco.getNome()) || !codigoMunicipio.equals(bairroSalvoNoBanco.getMunicipio().getCodigoMunicipio())) validaNomePorMunicipio(nome, municipio);

		bairroSalvoNoBanco.setNome(nome);
		bairroSalvoNoBanco.setStatus(status);
		bairroSalvoNoBanco.setMunicipio(municipio);

		bairroRepository.save(bairroSalvoNoBanco);
		return listar();
	}

	public List<BairroDtoGet> inativar(Integer codigoBairro) {
		Bairro bairro = buscarPorCodigoBairro(codigoBairro);
		bairro.setStatus(Status.INATIVO.getId());
		bairroRepository.save(bairro);
		return listar();
	}

	public List<BairroDtoGet> converterBairros(List<Bairro> bairros){
		return bairros
				.stream()
				.map( bairro -> {
					Integer codigoBairro = bairro.getCodigoBairro();
					String nome = bairro.getNome();
					Integer status = bairro.getStatus();
					Integer codigoMunicipio = bairro.getMunicipio().getCodigoMunicipio();

					BairroDtoGet bairroDtoGet = new BairroDtoGet();
					bairroDtoGet.setCodigoBairro(codigoBairro);
					bairroDtoGet.setNome(nome);
					bairroDtoGet.setStatus(status);
					bairroDtoGet.setCodigoMunicipio(codigoMunicipio);
					return bairroDtoGet;
				}).collect(Collectors.toList());
	}

	public Bairro buscarPorCodigoBairro(Integer codigoBairro) {
		return bairroRepository.findByCodigoBairro(codigoBairro).orElseThrow(
				() -> new EntityNotFoundException("Código Bairro não encontrado: " + codigoBairro));
	}

	public Municipio buscarPorCodigoMunicipio(Integer codigoMunicipio) {
		return municipioRepository.findByCodigoMunicipio(codigoMunicipio).orElseThrow(
				() -> new EntityNotFoundException("Código Município não encontrado: " + codigoMunicipio));
	}

	public void validaNomePorMunicipio(String nome, Municipio municipio) {
		if (bairroRepository.existsByNomeAndMunicipio(nome, municipio)) throw new NegocioException(String.format("Já existe um bairro cadastrado com o nome %s e codigoMunicipio %s. Você não pode cadastrar dois bairros com o mesmo nome para o mesmo Código de Município.", nome, municipio.getCodigoMunicipio()));
	}

	public void validaStatus(Integer status) {
		if (status != 1 && status != 2) throw new NegocioException("Status inválido. O status deve ser 1 ou 2");
	}
}
