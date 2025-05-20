package br.com.catedral.visitacao.dto;

import br.com.catedral.visitacao.constants.StatusPagamentoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record PagamentoStatusDTO(
    @NotNull(message = "Status do pagamento não pode ser nulo")
    StatusPagamentoEnum statusPagamentoEnum,

    LocalDateTime dataPagamento,

    @Size(max = 50)
    @NotBlank(message = "Método de pagamento não pode ser nulo")
    String metodoPagamento

) {
    public static PagamentoStatusDTO toDto(
        StatusPagamentoEnum status, LocalDateTime dataPagamento, String metodoPagamento) {
        return new PagamentoStatusDTO(status, dataPagamento, metodoPagamento);
    }
}
