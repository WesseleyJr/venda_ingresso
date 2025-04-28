package br.com.catedral.visitacao.dto;

import java.time.LocalDate;

import br.com.catedral.visitacao.constants.GeneroEnum;
import br.com.catedral.visitacao.model.Cliente;
import br.com.catedral.visitacao.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClienteInserirDTO(
		
		@NotBlank(message = "Nome completo não pode ser nulo")
		String nomeCompleto,
		
		@Size(max = 14)
		@NotBlank(message = "Celular não pode ser nulo")
		String celular,
		
		@NotNull(message = "Data de Nascimento não pode ser nulo")
		LocalDate dataNascimento,
		
		String nomeResponsavel,
		
	    @NotNull(message = "Genero não pode ser nulo")
	    GeneroEnum genero,
	    
		@NotNull(message = "Id usuario não pode ser nulo")
	    Long idUsuario
	    ) {
	
	public Cliente toEntity(Cliente cliente) {
		cliente.setNomeCompleto(nomeCompleto);
		cliente.setDataNascimento(dataNascimento);
		cliente.setNomeResponsavel(nomeResponsavel);
		cliente.setCelular(celular);
		cliente.setGenero(genero);
		
		Usuario usuario = new Usuario();
		usuario.setId(this.idUsuario);
		cliente.setUsuario(usuario);

		return cliente;
	}
	
	public static ClienteInserirDTO toDto(Cliente cliente) {
		return new ClienteInserirDTO(
				cliente.getNomeCompleto(),
				cliente.getCelular(),
				cliente.getDataNascimento(),
				cliente.getNomeResponsavel(),
				cliente.getGenero(),
				cliente.getUsuario().getId()
				
				);
	}

}
