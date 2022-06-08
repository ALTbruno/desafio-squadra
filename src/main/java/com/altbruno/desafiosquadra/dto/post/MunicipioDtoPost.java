package com.altbruno.desafiosquadra.dto.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MunicipioDtoPost {

	@NotNull
	private Integer codigoUF;
	@NotBlank
	private String nome;
	@NotNull
	private Integer status;

	public Integer getCodigoUF() {
		return codigoUF;
	}

	public void setCodigoUF(Integer codigoUF) {
		this.codigoUF = codigoUF;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
