package com.altbruno.desafiosquadra.dto.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EnderecoDtoPost {

	@NotNull
	private Integer codigoBairro;

	@NotBlank
	private String nomeRua;

	@NotBlank
	private String numero;

	private String complemento;

	@NotBlank
	private String cep;

	public Integer getCodigoBairro() {
		return codigoBairro;
	}

	public void setCodigoBairro(Integer codigoBairro) {
		this.codigoBairro = codigoBairro;
	}

	public String getNomeRua() {
		return nomeRua;
	}

	public void setNomeRua(String nomeRua) {
		this.nomeRua = nomeRua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
}
