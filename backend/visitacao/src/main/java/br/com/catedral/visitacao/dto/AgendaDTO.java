package br.com.catedral.visitacao.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.catedral.visitacao.model.Agenda;
import jakarta.validation.constraints.NotNull;

public record AgendaDTO(

    Long id,

    @NotNull(message = "Data e Hora não pode ser nulo")
    LocalDateTime dataHora,

    @NotNull(message = "Preço não pode ser nulo")
    Double preco,

    @NotNull(message = "Capacidade não pode ser nulo")
    Integer capacidade,

    Set<IngressoDTO> ingressos,

    @NotNull(message = "Nome do guia não pode ser nulo")
    String nomeGuia

) {

    public static AgendaDTO toDto(Agenda agenda) {
        Set<IngressoDTO> ingressoDTOs = agenda.getIngressos().stream()
            .map(IngressoDTO::toDto)
            .collect(Collectors.toSet());

        return new AgendaDTO(
            agenda.getId(),
            agenda.getDataHora(),
            agenda.getPreco(),
            agenda.getCapacidade(),
            ingressoDTOs,
            agenda.getGuia().getNomeCompleto()
        );
    }

    public Agenda toEntity(Agenda agenda) {
        agenda.setId(id);
        agenda.setDataHora(dataHora);
        agenda.setPreco(preco);
        agenda.setCapacidade(capacidade);
        return agenda;
    }
}
