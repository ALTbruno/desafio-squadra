package com.altbruno.desafiosquadra.service;

import com.altbruno.desafiosquadra.exception.NegocioException;
import com.altbruno.desafiosquadra.model.Status;
import com.altbruno.desafiosquadra.model.UF;
import com.altbruno.desafiosquadra.repository.UFRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@Service
public class UFService {

	@Autowired
	private UFRepository ufRepository;

	public void validaNomeSiglaStatus(UF uf) {
		String nome = uf.getNome().toUpperCase();
		String sigla = uf.getSigla().toUpperCase();
		if (ufRepository.existsByNome(nome)) throw new NegocioException(String.format("Já existe um estado com o nome %s. Você não pode cadastrar dois estados com o mesmo nome.", nome));
		if (ufRepository.existsBySigla(sigla)) throw new NegocioException(String.format("Já existe um estado com a sigla %s. Você não pode cadastrar dois estados com a mesma sigla.", sigla));
		if (uf.getStatus() != 1 && uf.getStatus() != 2) throw new NegocioException("Status inválido. O status deve ser 1 ou 2");
	}

	public List<UF> salvar(UF uf){
		validaNomeSiglaStatus(uf);
		uf.setNome(uf.getNome().toUpperCase());
		uf.setSigla(uf.getSigla().toUpperCase());
		ufRepository.save(uf);
		return listar();
	}

	public UF buscarPorCodigoUF(Integer codigoUF) {
		return ufRepository.findByCodigoUF(codigoUF).orElseThrow(
				() -> new EntityNotFoundException("Código UF não encontrado: " + codigoUF));
	}

	public List<UF> listar(Example<UF> example) {
		List<UF> ufList = ufRepository.findAll(example);
		Collections.sort(ufList);
		return ufList;
	}

	public List<UF> listar() {
		return ufRepository.findAllByOrderByCodigoUFDesc();
	}

	public List<UF> atualizar(UF uf) {
		UF ufJaCadastradaNoBanco = buscarPorCodigoUF(uf.getCodigoUF());
		validaNomeSiglaStatus(uf);
		ufJaCadastradaNoBanco.setNome(uf.getNome().toUpperCase());
		ufJaCadastradaNoBanco.setSigla(uf.getSigla().toUpperCase());
		ufJaCadastradaNoBanco.setStatus(uf.getStatus());
		ufRepository.save(ufJaCadastradaNoBanco);
		return listar();
	}
	public List<UF> inativar(Integer codigoUF) {
		UF uf = buscarPorCodigoUF(codigoUF);
		uf.setStatus(Status.INATIVO.getId());
		ufRepository.save(uf);
		return listar();
	}
}
