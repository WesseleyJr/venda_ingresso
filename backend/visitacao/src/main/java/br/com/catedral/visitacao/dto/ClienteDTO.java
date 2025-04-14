package br.com.catedral.visitacao.dto;

import java.time.LocalDate;
import java.util.Set;


import br.com.catedral.visitacao.constants.GeneroEnum;
import br.com.catedral.visitacao.model.Cliente;
import br.com.catedral.visitacao.model.Ingresso;
import br.com.catedral.visitacao.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClienteDTO(
		Long id,
		@NotBlank(message = "Nome completo não pode ser nulo")
		String nomeCompleto,
		@Size(max = 14)
		@NotBlank(message = "Celular não pode ser nulo")
		String celular,
		@NotNull(message = "Data de Nascimento não pode ser nulo")
		LocalDate dataNascimento,
		@NotBlank(message = "Nome do responsável não pode ser nulo")
		String nomeResponsavel,
	    @NotNull(message = "Gênero não pode ser nulo")
	    GeneroEnum genero,
	    @NotNull(message = "Os ingressos não podem estar vazios") 
	    Set<Ingresso> ingressos,
		@NotNull(message = "Email do usuário não pode ser nulo")
		@Email
	    String emailUsuario
) {
	public Cliente toEntity(Cliente cliente, Usuario usuario) {
		cliente.setId(id);
		cliente.setNomeCompleto(nomeCompleto);
		cliente.setDataNascimento(dataNascimento);
		cliente.setNomeResponsavel(nomeResponsavel);
		cliente.setCelular(celular);
		cliente.setGenero(genero);
		cliente.setIngressos(ingressos);
		cliente.setUsuario(usuario);
		return cliente;
	}

	public static ClienteDTO toDto(Cliente cliente) {
		return new ClienteDTO(
			cliente.getId(),
			cliente.getNomeCompleto(),
			cliente.getCelular(),
			cliente.getDataNascimento(),
			cliente.getNomeResponsavel(),
			cliente.getGenero(),
			cliente.getIngressos(),
			cliente.getUsuario().getEmail()
		);
	}
}
