package br.com.catedral.visitacao.dto;

import br.com.catedral.visitacao.constants.StatusPagamentoEnum;
import br.com.catedral.visitacao.model.Pagamento;
import jakarta.validation.constraints.NotNull;

public record PagamentoStatusDTO(
    @NotNull(message = "Status do pagamento não pode ser nulo")
    StatusPagamentoEnum statusPagamentoEnum
) {
    public static PagamentoStatusDTO toDto(Pagamento pagamento) {
        return new PagamentoStatusDTO(pagamento.getStatusPagamentoEnum());
    }
}
