
package com.altbruno.desafiosquadra.dto.put;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class PessoaDtoPut {

	@NotNull
	private Integer codigoPessoa;

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

	private List<EnderecoDtoPut> enderecos;

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

	public List<EnderecoDtoPut> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoDtoPut> enderecos) {
		this.enderecos = enderecos;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
