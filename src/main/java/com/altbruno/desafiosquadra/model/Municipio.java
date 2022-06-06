package com.altbruno.desafiosquadra.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_MUNICIPIO")
public class Municipio implements Comparable<Municipio> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_municipio")
	@SequenceGenerator(name = "sequence_id_municipio", allocationSize = 1, sequenceName = "sequence_municipio")
	private Integer codigoMunicipio;

	@Column(length = 256, nullable = false)
	@NotBlank
	private String nome;

	@NotNull
	private Integer status;

	@ManyToOne
	@JoinColumn(name = "codigo_uf")
	private UF uf;

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

	public UF getUf() {
		return uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}

	@Override
	public int compareTo(Municipio outroMunicipio) {
		return outroMunicipio.getCodigoMunicipio().compareTo(this.codigoMunicipio);
	}
}
