package br.com.catedral.visitacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.catedral.visitacao.dto.PixPaymentRequestDTO;
import br.com.catedral.visitacao.dto.PixPaymentResponseDTO;
import br.com.catedral.visitacao.service.PixPaymentService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/pix")
public class PixPaymentController {

    @Autowired
    private PixPaymentService pixPaymentService;

//    @PostMapping("/create")
//    public Mono<PixPaymentResponseDTO> createPixPayment(@RequestBody PixPaymentRequestDTO requestDTO) {
//        return pixPaymentService.createPixPayment(requestDTO);
//    }
}
