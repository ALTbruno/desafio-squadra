package com.altbruno.desafiosquadra.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "TB_ENDERECO")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_endereco")
	@SequenceGenerator(name = "sequence_id_endereco", allocationSize = 1, sequenceName = "sequence_endereco")
	private Integer codigoEndereco;

	@NotBlank
	@Column(length = 256, nullable = false)
	private String nomeRua;

	@NotBlank
	@Column(length = 10, nullable = false)
	private String numero;

	@Column(length = 20, nullable = true)
	private String complemento;

	@NotBlank
	@Column(length = 10, nullable = false)
	private String cep;

	@ManyToOne
	@JoinColumn(name = "codigo_bairro")
	private Bairro bairro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@JoinColumn(name = "codigo_pessoa")
	private Pessoa pessoa;

	public Integer getCodigoEndereco() {
		return codigoEndereco;
	}

	public void setCodigoEndereco(Integer codigoEndereco) {
		this.codigoEndereco = codigoEndereco;
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

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public com.altbruno.desafiosquadra.model.Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(com.altbruno.desafiosquadra.model.Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
