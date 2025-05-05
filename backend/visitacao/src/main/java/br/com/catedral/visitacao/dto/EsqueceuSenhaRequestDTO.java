package br.com.catedral.visitacao.dto;

import jakarta.validation.constraints.NotBlank;

public class EsqueceuSenhaRequestDTO {
	@NotBlank(message = "Email não pode ser nulo")
    private String email;

    public EsqueceuSenhaRequestDTO() {
    }

    public EsqueceuSenhaRequestDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
