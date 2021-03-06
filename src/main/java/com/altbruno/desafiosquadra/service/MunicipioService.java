package com.altbruno.desafiosquadra.service;

import com.altbruno.desafiosquadra.dto.get.MunicipioDtoGet;
import com.altbruno.desafiosquadra.dto.post.MunicipioDtoPost;
import com.altbruno.desafiosquadra.exception.NegocioException;
import com.altbruno.desafiosquadra.model.Municipio;
import com.altbruno.desafiosquadra.model.Status;
import com.altbruno.desafiosquadra.model.UF;
import com.altbruno.desafiosquadra.repository.MunicipioRepository;
import com.altbruno.desafiosquadra.repository.UFRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MunicipioService {

	@Autowired
	private MunicipioRepository municipioRepository;

	@Autowired
	private UFRepository ufRepository;

	public List<MunicipioDtoGet> cadastrar(MunicipioDtoPost municipioDtoPost) {

		UF uf = buscarPorCodigoUF(municipioDtoPost.getCodigoUF());
		String nome = municipioDtoPost.getNome().toUpperCase();
		Integer status = municipioDtoPost.getStatus();

		validaStatus(status);
		validaNomePorUF(nome, uf);

		Municipio municipio = new Municipio();
		municipio.setNome(nome);
		municipio.setStatus(status);
		municipio.setUf(uf);
		municipioRepository.save(municipio);
		return listar();
	}

	public List<MunicipioDtoGet> listar() {
		List<Municipio> municipios = municipioRepository.findAllByOrderByCodigoMunicipioDesc();
		return converterMunicipios(municipios);
	}

	public List<MunicipioDtoGet> listar(Example<MunicipioDtoGet> example) {
		MunicipioDtoGet municipioDtoGet = example.getProbe();

		Municipio municipio = new Municipio();
		municipio.setCodigoMunicipio(municipioDtoGet.getCodigoMunicipio());
		municipio.setNome(municipioDtoGet.getNome());
		municipio.setStatus(municipioDtoGet.getStatus());
		if (municipioDtoGet.getCodigoUF() != null) municipio.setUf(buscarPorCodigoUF(municipioDtoGet.getCodigoUF()));
		Example<Municipio> municipioExample = Example.of(municipio, example.getMatcher());

		List<Municipio> municipios = municipioRepository.findAll(municipioExample);
		Collections.sort(municipios);
		return converterMunicipios(municipios);
	}

	public List<MunicipioDtoGet> atualizar(MunicipioDtoGet municipioDtoGet) {
		Municipio municipioJaCadastradoNoBanco = buscarPorCodigoMunicipio(municipioDtoGet.getCodigoMunicipio());

		String nome = municipioDtoGet.getNome().toUpperCase();
		Integer status = municipioDtoGet.getStatus();
		Integer codigoUF = municipioDtoGet.getCodigoUF();
		UF uf = buscarPorCodigoUF(codigoUF);

		if (!nome.equalsIgnoreCase(municipioJaCadastradoNoBanco.getNome()) || !codigoUF.equals(municipioJaCadastradoNoBanco.getUf().getCodigoUF()))
			validaNomePorUF(nome, uf);

		municipioJaCadastradoNoBanco.setNome(nome);
		municipioJaCadastradoNoBanco.setStatus(status);
		municipioJaCadastradoNoBanco.setUf(uf);
		municipioRepository.save(municipioJaCadastradoNoBanco);

		return listar();
	}

	public List<MunicipioDtoGet> inativar(Integer codigoMunicipio) {
		Municipio municipio = buscarPorCodigoMunicipio(codigoMunicipio);
		municipio.setStatus(Status.INATIVO.getId());
		municipioRepository.save(municipio);
		return listar();
	}

	public List<MunicipioDtoGet> converterMunicipios(List<Municipio> municipios){
		return municipios
				.stream()
				.map( municipio -> {
					Integer codigoMunicipio = municipio.getCodigoMunicipio();
					String nome = municipio.getNome();
					Integer status = municipio.getStatus();
					Integer codigoUF = municipio.getUf().getCodigoUF();

					MunicipioDtoGet municipioDtoGet = new MunicipioDtoGet();
					municipioDtoGet.setCodigoMunicipio(codigoMunicipio);
					municipioDtoGet.setNome(nome);
					municipioDtoGet.setStatus(status);
					municipioDtoGet.setCodigoUF(codigoUF);
					return municipioDtoGet;
				}).collect(Collectors.toList());
	}

	public UF buscarPorCodigoUF(Integer codigoUF) {
		return ufRepository.findByCodigoUF(codigoUF).orElseThrow(
				() -> new EntityNotFoundException("C??digo UF n??o encontrado: " + codigoUF));
	}

	public Municipio buscarPorCodigoMunicipio(Integer codigoMunicipio) {
		return municipioRepository.findByCodigoMunicipio(codigoMunicipio).orElseThrow(
				() -> new EntityNotFoundException("C??digo Munic??pio n??o encontrado: " + codigoMunicipio));
	}

	public void validaNomePorUF(String nome, UF uf) {
		if (municipioRepository.existsByNomeAndUf(nome, uf)) throw new NegocioException(String.format("J?? existe um munic??pio cadastrado com o nome %s e codigoUF %s. Voc?? n??o pode cadastrar dois munic??pios com o mesmo nome para o mesmo C??digo de UF.", nome, uf.getCodigoUF()));
	}

	public void validaStatus(Integer status) {
		if (status != 1 && status != 2) throw new NegocioException("Status inv??lido. O status deve ser 1 ou 2");
	}

}
