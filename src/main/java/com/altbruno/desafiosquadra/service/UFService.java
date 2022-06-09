package com.altbruno.desafiosquadra.service;

import com.altbruno.desafiosquadra.dto.post.UFDtoPost;
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

	public List<UF> salvar(UFDtoPost ufDTO){
		String nome = ufDTO.getNome().toUpperCase();
		String sigla = ufDTO.getSigla().toUpperCase();
		Integer status = ufDTO.getStatus();
		validaNomeCadastrado(nome);
		validaSiglaCadastrada(sigla);
		validaStatus(status);
		UF uf = new UF();
		uf.setNome(nome);
		uf.setSigla(sigla);
		uf.setStatus(status);
		ufRepository.save(uf);
		return listar();
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

		String nome = uf.getNome().toUpperCase();
		String sigla = uf.getSigla().toUpperCase();
		Integer status = uf.getStatus();

		if (!nome.equalsIgnoreCase(ufJaCadastradaNoBanco.getNome()))
			validaNomeCadastrado(nome);
		if (!sigla.equalsIgnoreCase(uf.getSigla()))
			validaSiglaCadastrada(sigla);
		validaStatus(status);

		ufJaCadastradaNoBanco.setNome(nome);
		ufJaCadastradaNoBanco.setSigla(sigla);
		ufJaCadastradaNoBanco.setStatus(status);
		ufRepository.save(ufJaCadastradaNoBanco);
		return listar();
	}
	public List<UF> inativar(Integer codigoUF) {
		UF uf = buscarPorCodigoUF(codigoUF);
		uf.setStatus(Status.INATIVO.getId());
		ufRepository.save(uf);
		return listar();
	}

	public UF buscarPorCodigoUF(Integer codigoUF) {
		return ufRepository.findByCodigoUF(codigoUF).orElseThrow(
				() -> new EntityNotFoundException("Código UF não encontrado: " + codigoUF));
	}

	public void validaNomeCadastrado(String nome) {
		if (ufRepository.existsByNome(nome)) throw new NegocioException(String.format("Já existe um estado com o nome %s. Você não pode cadastrar dois estados com o mesmo nome.", nome));
	}

	public void validaSiglaCadastrada(String sigla) {
		if (ufRepository.existsBySigla(sigla)) throw new NegocioException(String.format("Já existe um estado com a sigla %s. Você não pode cadastrar dois estados com a mesma sigla.", sigla));
	}

	public void validaStatus(Integer status) {
		if (status != 1 && status != 2) throw new NegocioException("Status inválido. O status deve ser 1 ou 2");
	}
}
