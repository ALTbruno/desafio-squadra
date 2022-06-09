package com.altbruno.desafiosquadra.dto.get;

import com.altbruno.desafiosquadra.model.UF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MunicipioDtoBuscaPorCodigoPessoa {

	@NotNull
	private Integer codigoMunicipio;
	@NotNull
	private Integer codigoUF;
	@NotBlank
	private String nome;
	@NotNull
	private Integer status;

	private UF uf;

	public Integer getCodigoMunicipio() {
		return codigoMunicipio;
	}

	public void setCodigoMunicipio(Integer codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

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

	public UF getUf() {
		return uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}
}
