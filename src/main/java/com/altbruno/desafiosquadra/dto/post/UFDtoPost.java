package com.altbruno.desafiosquadra.dto.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UFDtoPost {

	@NotBlank
	private String nome;
	@NotBlank
	private String sigla;
	@NotNull
	private Integer status;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
