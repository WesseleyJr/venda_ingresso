package br.com.catedral.visitacao.dto;

import java.time.LocalDateTime;

import br.com.catedral.visitacao.model.Agenda;
import br.com.catedral.visitacao.model.Guia;
import jakarta.validation.constraints.NotNull;

public record AgendaInserirDTO(


    @NotNull(message = "Data e Hora não pode ser nulo")
    LocalDateTime dataHora,

    @NotNull(message = "Preço não pode ser nulo")
    Double preco,

    @NotNull(message = "Capacidade não pode ser nulo")
    Integer capacidade,

    @NotNull(message = "Id do guia não pode ser nulo")
    Long idGuia

) {

    public Agenda toEntity(Agenda agenda) {
        agenda.setDataHora(dataHora);
        agenda.setPreco(preco);
        agenda.setCapacidade(capacidade);

        Guia guia = new Guia();
        guia.setId(this.idGuia);
        agenda.setGuia(guia);

        return agenda;
    }

    public static AgendaInserirDTO toDto(Agenda agenda) {
        return new AgendaInserirDTO(
            agenda.getDataHora(),
            agenda.getPreco(),
            agenda.getCapacidade(),
            agenda.getGuia().getId()
        );
    }
}
