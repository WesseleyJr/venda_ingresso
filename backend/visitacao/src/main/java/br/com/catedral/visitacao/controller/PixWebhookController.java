package br.com.catedral.visitacao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.catedral.visitacao.dto.PixWebhookDTO;
import br.com.catedral.visitacao.service.PagamentoService;

@RestController
@RequestMapping("/webhook/pix")
public class PixWebhookController {

    private static final Logger logger = LoggerFactory.getLogger(PixWebhookController.class);

    @Autowired
    private PagamentoService pagamentoService;
    @PostMapping("/webhook")
    public ResponseEntity<Void> webhookReceber(
        @RequestBody PixWebhookDTO webhook,
        @RequestHeader(value = "X-Hub-Signature", required = false) String signature
    ) {
        String paymentId = webhook.getData().getId();
        pagamentoService.processarPagamentoPorId(paymentId); 

        return ResponseEntity.ok().build();
    }
}