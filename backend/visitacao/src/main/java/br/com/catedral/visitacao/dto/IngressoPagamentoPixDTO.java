package br.com.catedral.visitacao.dto;

import java.util.List;

public class IngressoPagamentoPixDTO {

    private List<IngressoDTO> ingressos;
    private PixPaymentResponseDTO pix;

    public IngressoPagamentoPixDTO(List<IngressoDTO> ingressos, PixPaymentResponseDTO pix) {
        this.ingressos = ingressos;
        this.pix = pix;
    }

    public List<IngressoDTO> getIngressos() {
        return ingressos;
    }

    public void setIngressos(List<IngressoDTO> ingressos) {
        this.ingressos = ingressos;
    }

    public PixPaymentResponseDTO getPix() {
        return pix;
    }

    public void setPix(PixPaymentResponseDTO pix) {
        this.pix = pix;
    }
}
