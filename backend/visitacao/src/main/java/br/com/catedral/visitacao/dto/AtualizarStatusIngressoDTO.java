package br.com.catedral.visitacao.dto;

import br.com.catedral.visitacao.constants.StatusIngressoEnum;
import jakarta.validation.constraints.NotNull;

public class AtualizarStatusIngressoDTO {

    @NotNull(message = "O status não pode ser nulo")
    private StatusIngressoEnum status;

    public StatusIngressoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusIngressoEnum status) {
        this.status = status;
    }
}
