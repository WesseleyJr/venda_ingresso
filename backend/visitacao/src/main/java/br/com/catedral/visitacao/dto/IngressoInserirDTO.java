package br.com.catedral.visitacao.dto;

import java.time.LocalDate;

import br.com.catedral.visitacao.constants.StatusIngressoEnum;
import br.com.catedral.visitacao.model.Agenda;
import br.com.catedral.visitacao.model.Ingresso;
import br.com.catedral.visitacao.model.Pagamento;
import br.com.catedral.visitacao.model.Usuario;
import jakarta.validation.constraints.NotNull;

public record IngressoInserirDTO(

    @NotNull(message = "Status do ingresso não pode ser nulo")
    StatusIngressoEnum statusIngressoEnum,

    @NotNull(message = "ID da agenda não pode ser nulo")
    Long idAgenda,

    @NotNull(message = "ID do pagamento não pode ser nulo")
    Long idPagamento,

    String nomeCompleto,

    String celular,

    LocalDate dataNascimento,

    String nomeResponsavel,
    
    @NotNull(message = "ID do usuário não pode ser nulo")
    Long idUsuario

) {

    public Ingresso toEntity(Agenda agenda, Pagamento pagamento, Usuario usuario) {
        Ingresso ingresso = new Ingresso();
        ingresso.setStatusIngressoEnum(statusIngressoEnum);
        ingresso.setAgenda(agenda);
        ingresso.setPagamento(pagamento);
        ingresso.setNomeCompleto(nomeCompleto);
        ingresso.setCelular(celular);
        ingresso.setDataNascimento(dataNascimento);
        ingresso.setNomeResponsavel(nomeResponsavel);
        ingresso.setUsuario(usuario);
        return ingresso;
    }

    public static IngressoInserirDTO toDto(Ingresso ingresso) {
        return new IngressoInserirDTO(
            ingresso.getStatusIngressoEnum(),
            ingresso.getAgenda().getId(),
            ingresso.getPagamento().getId(),
            ingresso.getNomeCompleto(),
            ingresso.getCelular(),
            ingresso.getDataNascimento(),
            ingresso.getNomeResponsavel(),
            ingresso.getUsuario().getId()
        );
    }
}
