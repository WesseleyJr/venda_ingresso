package br.com.catedral.visitacao.dto;

import br.com.catedral.visitacao.constants.StatusIngressoEnum;
import br.com.catedral.visitacao.model.QrCode;
import br.com.catedral.visitacao.model.Pagamento;
import br.com.catedral.visitacao.model.Ingresso;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record IngressoDTO(

    Long id,

    @NotNull(message = "Status do ingresso não pode ser nulo")
    StatusIngressoEnum statusIngressoEnum,

    LocalDateTime dataHora,

    @NotNull(message = "Pagamento não pode ser nulo")
    Long idPagamento,

    QrCode qrCode,

    @NotBlank(message = "Nome completo não pode ser nulo")
    String nomeCompleto,

    @Size(max = 14, message = "Celular não pode ter mais de 14 caracteres")
    @NotBlank(message = "Celular não pode ser nulo")
    String celular,

    @NotNull(message = "Data de Nascimento não pode ser nulo")
    LocalDate dataNascimento,

    String nomeResponsavel,

    String emailUsuario

) {

    public static IngressoDTO toDto(Ingresso ingresso) {
        return new IngressoDTO(
            ingresso.getId(),
            ingresso.getStatusIngressoEnum(),
            ingresso.getAgenda().getDataHora(),
            ingresso.getPagamento().getId(),
            ingresso.getQrCode(),
            ingresso.getNomeCompleto(),
            ingresso.getCelular(),
            ingresso.getDataNascimento(),
            ingresso.getNomeResponsavel(),
            ingresso.getUsuario().getEmail()
        );
    }
}
