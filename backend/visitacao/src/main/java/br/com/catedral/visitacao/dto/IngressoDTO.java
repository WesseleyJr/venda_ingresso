package br.com.catedral.visitacao.dto;

import br.com.catedral.visitacao.constants.StatusIngressoEnum;
import br.com.catedral.visitacao.model.Ingresso;
import br.com.catedral.visitacao.model.Pagamento;
import br.com.catedral.visitacao.model.QrCode;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

public record IngressoDTO(

    Long id,

    @NotNull(message = "Status do ingresso não pode ser nulo")
    StatusIngressoEnum statusIngressoEnum,

    LocalDateTime dataHora,

    String nomeCompletoCliente,

    Set<Pagamento> pagamentos,

    Set<QrCode> qrCode

) {

    public static IngressoDTO toDto(Ingresso ingresso) {
        return new IngressoDTO(
            ingresso.getId(),
            ingresso.getStatusIngressoEnum(),
            ingresso.getAgenda().getDataHora(),
            ingresso.getCliente().getNomeCompleto(),
            ingresso.getPagamentos(),
            ingresso.getQrCodes()
        );
    }
}
