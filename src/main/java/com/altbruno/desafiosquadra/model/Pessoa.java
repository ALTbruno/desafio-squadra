package com.altbruno.desafiosquadra.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "TB_PESSOA")
public class Pessoa implements Comparable<Pessoa>{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_pessoa")
	@SequenceGenerator(name = "sequence_id_pessoa", allocationSize = 1, sequenceName = "sequence_pessoa")
	private Integer codigoPessoa;

	@NotBlank
	@Column(length = 256, nullable = false)
	private String nome;

	@NotBlank
	@Column(length = 256, nullable = false)
	private String sobrenome;

	@NotNull
	private Integer idade;

	@NotBlank
	@Column(length = 50, nullable = false, unique = true)
	private String login;

	@NotBlank
	@Column(length = 50, nullable = false)
	private String senha;

	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Endereco> enderecos;

	@NotNull
	private Integer status;

	public Integer getCodigoPessoa() {
		return codigoPessoa;
	}

	public void setCodigoPessoa(Integer codigoPessoa) {
		this.codigoPessoa = codigoPessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	@Override
	public int compareTo(Pessoa outraPessoa) {
		return outraPessoa.getCodigoPessoa().compareTo(this.codigoPessoa);
	}
}
