package com.altbruno.desafiosquadra.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_UF")
public class UF implements Comparable<UF> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_uf")
	@SequenceGenerator(name = "sequence_id_uf", allocationSize = 1, sequenceName = "sequence_uf")
	@Column(name = "codigo_uf")
	private Integer codigoUF;

	@Column(length = 60, nullable = false, unique = true)
	@NotBlank
	private String nome;

	@Column(length = 3, nullable = false, unique = true)
	@NotBlank
	private String sigla;

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

	@Override
	public String toString() {
		return "UF{" +
				"codigoUF=" + codigoUF +
				", nome='" + nome + '\'' +
				", sigla='" + sigla + '\'' +
				", status=" + status +
				'}';
	}

	@Override
	public int compareTo(UF outraUF) {
		return outraUF.getCodigoUF().compareTo(this.codigoUF);
	}
}
