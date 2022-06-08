package com.altbruno.desafiosquadra.dto.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BairroDtoPost {

	@NotNull
	private Integer codigoMunicipio;
	@NotBlank
	private String nome;
	@NotNull
	private Integer status;

	public Integer getCodigoMunicipio() {
		return codigoMunicipio;
	}

	public void setCodigoMunicipio(Integer codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
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
