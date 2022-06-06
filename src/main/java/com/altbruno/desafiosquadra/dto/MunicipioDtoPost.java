package com.altbruno.desafiosquadra.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MunicipioDtoPost {

	@NotBlank
	private String nome;
	@NotNull
	private Integer status;
	@NotNull
	private Integer codigoUF;

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

	public Integer getCodigoUF() {
		return codigoUF;
	}

	public void setCodigoUF(Integer codigoUF) {
		this.codigoUF = codigoUF;
	}
}
