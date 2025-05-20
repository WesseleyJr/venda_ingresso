package br.com.catedral.visitacao.dto;

import java.util.List;

public record IngressoComCheckoutDTO(
    List<IngressoDTO> ingressos,
    String urlCheckout
) {}
