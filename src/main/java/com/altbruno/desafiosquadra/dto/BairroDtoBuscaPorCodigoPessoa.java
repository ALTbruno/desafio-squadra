package com.altbruno.desafiosquadra.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BairroDtoBuscaPorCodigoPessoa {

	@NotNull
	private Integer codigoBairro;

	@NotNull
	private Integer codigoMunicipio;

	@NotBlank
	private String nome;

	@NotNull
	private Integer status;

	private MunicipioDtoBuscaPorCodigoPessoa municipioDtoBuscaPorCodigoPessoa;

	public Integer getCodigoBairro() {
		return codigoBairro;
	}

	public void setCodigoBairro(Integer codigoBairro) {
		this.codigoBairro = codigoBairro;
	}

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

	public MunicipioDtoBuscaPorCodigoPessoa getMunicipioDtoBuscaPorCodigoPessoa() {
		return municipioDtoBuscaPorCodigoPessoa;
	}

	public void setMunicipioDtoBuscaPorCodigoPessoa(MunicipioDtoBuscaPorCodigoPessoa municipioDtoBuscaPorCodigoPessoa) {
		this.municipioDtoBuscaPorCodigoPessoa = municipioDtoBuscaPorCodigoPessoa;
	}
}
