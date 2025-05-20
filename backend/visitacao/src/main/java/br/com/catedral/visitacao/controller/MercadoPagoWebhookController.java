package br.com.catedral.visitacao.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.catedral.visitacao.service.MercadoPagoService;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/webhook/mercadopago")
public class MercadoPagoWebhookController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @PostMapping
    public ResponseEntity<Void> receberNotificacao(
            @RequestBody String payload,
            @RequestHeader("x-signature") String signatureHeader) {

        System.out.println("Recebido webhook Mercado Pago: " + payload);

        try {
            mercadoPagoService.processarNotificacao(payload, signatureHeader);
            return ResponseEntity.ok().build();
        } catch (SecurityException e) {
            System.err.println("Assinatura inválida: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
