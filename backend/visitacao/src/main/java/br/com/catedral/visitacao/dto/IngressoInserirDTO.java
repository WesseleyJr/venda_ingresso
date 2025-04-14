package br.com.catedral.visitacao.dto;

import br.com.catedral.visitacao.constants.StatusIngressoEnum;
import br.com.catedral.visitacao.model.Agenda;
import br.com.catedral.visitacao.model.Cliente;
import br.com.catedral.visitacao.model.Ingresso;
import jakarta.validation.constraints.NotNull;

public record IngressoInserirDTO(

    @NotNull(message = "Status do ingresso não pode ser nulo")
    StatusIngressoEnum statusIngressoEnum,

    @NotNull(message = "ID da agenda não pode ser nulo")
    Long idAgenda,

    @NotNull(message = "ID do cliente não pode ser nulo")
    Long idCliente

) {

    public Ingresso toEntity(Agenda agenda, Cliente cliente) {
        Ingresso ingresso = new Ingresso();
        ingresso.setStatusIngressoEnum(statusIngressoEnum);
        ingresso.setAgenda(agenda);
        ingresso.setCliente(cliente);
        return ingresso;
    }

    public static IngressoInserirDTO toDto(Ingresso ingresso) {
        return new IngressoInserirDTO(
            ingresso.getStatusIngressoEnum(),
            ingresso.getAgenda().getId(),
            ingresso.getCliente().getId()
        );
    }
}
