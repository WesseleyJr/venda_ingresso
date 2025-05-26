package br.com.catedral.visitacao.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Size;

public class UsuarioCpfDTO {
	
	@CPF
	@Size(max = 11)
	private String cpf;
	

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
