package com.altbruno.desafiosquadra.dto.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class PessoaDtoPost {

	@NotBlank
	private String nome;

	@NotBlank
	private String sobrenome;

	@NotNull
	private Integer idade;

	@NotBlank
	private String login;

	@NotBlank
	private String senha;

	@NotNull
	private List<EnderecoDtoPost> enderecos;

	@NotNull
	private Integer status;

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

	public List<EnderecoDtoPost> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoDtoPost> enderecos) {
		this.enderecos = enderecos;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
