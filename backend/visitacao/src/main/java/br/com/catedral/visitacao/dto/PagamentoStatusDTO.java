package br.com.catedral.visitacao.dto;

import java.time.LocalDateTime;

import br.com.catedral.visitacao.constants.StatusPagamentoEnum;

public class PagamentoStatusDTO {
    private StatusPagamentoEnum statusPagamentoEnum;
    private LocalDateTime dataAprovacao;

    public PagamentoStatusDTO(StatusPagamentoEnum statusPagamentoEnum, LocalDateTime dataAprovacao) {
        this.statusPagamentoEnum = statusPagamentoEnum;
        this.dataAprovacao = dataAprovacao;
    }

    public StatusPagamentoEnum getStatusPagamentoEnum() {
        return statusPagamentoEnum;
    }

    public LocalDateTime getDataAprovacao() {
        return dataAprovacao;
    }
}
