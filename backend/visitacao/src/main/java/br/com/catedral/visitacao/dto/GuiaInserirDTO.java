package br.com.catedral.visitacao.dto;

import org.hibernate.validator.constraints.br.CPF;

import br.com.catedral.visitacao.model.Guia;
import br.com.catedral.visitacao.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GuiaInserirDTO(

    @NotBlank(message = "Nome completo não pode ser nulo")
    String nomeCompleto,

    @Size(max = 14)
    @NotBlank(message = "Celular não pode ser nulo")
    String celular,

    @Size(max = 11)
    @NotBlank(message = "CPF não pode ser nulo")
    @CPF
    String cpf,

    @NotNull(message = "Id do usuário não pode ser nulo")
    Long idUsuario

) {

    public Guia toEntity(Guia guia) {
        guia.setNomeCompleto(nomeCompleto);
        guia.setCelular(celular);
        guia.setCpf(cpf);

        Usuario usuario = new Usuario();
        usuario.setId(this.idUsuario);
        guia.setUsuario(usuario);

        return guia;
    }

    public static GuiaInserirDTO toDto(Guia guia) {
        return new GuiaInserirDTO(
            guia.getNomeCompleto(),
            guia.getCelular(),
            guia.getCpf(),
            guia.getUsuario().getId()
        );
    }
}
