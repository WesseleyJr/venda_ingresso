package br.com.catedral.visitacao.dto;

import org.hibernate.validator.constraints.br.CPF;

import br.com.catedral.visitacao.model.Guia;
import br.com.catedral.visitacao.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GuiaDTO(

    Long id,

    @NotBlank(message = "Nome completo não pode ser nulo")
    String nomeCompleto,

    @Size(max = 14)
    @NotBlank(message = "Celular não pode ser nulo")
    String celular,

    @Size(max = 11)
    @NotBlank(message = "CPF não pode ser nulo")
    @CPF
    String cpf,

    @NotNull(message = "Email do usuário não pode ser nulo")
    @Email
    String emailUsuario

) {

    public Guia toEntity(Guia guia, Usuario usuario) {
        guia.setId(id);
        guia.setNomeCompleto(nomeCompleto);
        guia.setCelular(celular);
        guia.setCpf(cpf);
        guia.setUsuario(usuario);
        return guia;
    }

    public static GuiaDTO toDto(Guia guia) {
        return new GuiaDTO(
            guia.getId(),
            guia.getNomeCompleto(),
            guia.getCelular(),
            guia.getCpf(),
            guia.getUsuario().getEmail()
        );
    }
}
