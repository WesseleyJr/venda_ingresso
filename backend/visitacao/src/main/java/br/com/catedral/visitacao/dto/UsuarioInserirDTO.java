package br.com.catedral.visitacao.dto;

import java.util.Set;

import br.com.catedral.visitacao.model.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;



public class UsuarioInserirDTO {

	@NotBlank(message = "Nome não pode ser nulo")
	private String nome;
	
	@NotBlank(message = "Email não pode ser nulo")
	@Email
	private String email;
	
	@NotBlank(message = "Senha não pode ser nulo")
	private String senha;
	
	@NotBlank(message = "Confirma senha não pode ser nulo")
	private String confirmaSenha;
	
	private Set<Perfil> perfis;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public Set<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

}