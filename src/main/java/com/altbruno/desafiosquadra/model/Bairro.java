package com.altbruno.desafiosquadra.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_BAIRRO")
public class Bairro implements Comparable<Bairro> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_bairro")
	@SequenceGenerator(name = "sequence_id_bairro", allocationSize = 1, sequenceName = "sequence_bairro")
	private Integer codigoBairro;

	@Column(length = 256, nullable = false)
	@NotBlank
	private String nome;

	@NotNull
	private Integer status;

	@ManyToOne
	@JoinColumn(name = "codigo_municipio")
	private Municipio municipio;

	public Integer getCodigoBairro() {
		return codigoBairro;
	}

	public void setCodigoBairro(Integer codigoBairro) {
		this.codigoBairro = codigoBairro;
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

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	@Override
	public int compareTo(Bairro outroBairro) {
		return outroBairro.getCodigoBairro().compareTo(this.codigoBairro);
	}
}
