package br.com.catedral.visitacao.controller;

import br.com.catedral.visitacao.dto.AtualizarStatusIngressoDTO;
import br.com.catedral.visitacao.dto.IngressoDTO;
import br.com.catedral.visitacao.dto.IngressoInserirDTO;
import br.com.catedral.visitacao.dto.IngressoPagamentoPixDTO;
import br.com.catedral.visitacao.service.IngressoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingresso")
public class IngressoController {

    @Autowired
    private IngressoService ingressoService;
    
    @Operation(summary = "Retorna todas os ingressos", description = "Lista todos os ingressos")
    @GetMapping
    public ResponseEntity<List<IngressoDTO>> listarTodos() {
        return ResponseEntity.ok(ingressoService.listarTodos());
    }

    @Operation(summary = "Retorna o ingresso pelo id", description = "Dado um determinado id, será retornado o ingresso")
    @GetMapping("/{id}")
    public ResponseEntity<IngressoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ingressoService.buscarPorId(id));
    }

    @Operation(summary = "Cadastro do ingresso", description = "Cadastro do ingresso dado o determinado BODY")
    @PostMapping
    public Mono<ResponseEntity<IngressoPagamentoPixDTO>> inserir(@RequestBody @Valid List<IngressoInserirDTO> dtos) {
        return ingressoService.inserirLista(dtos)
                .map(response -> ResponseEntity.ok(response));
    }


    @Operation(summary = "Atualiza o ingresso pelo id", description = "Dado um determinado id e as informações, será atualizado os dados de cadastro do ingresso")
    @PutMapping("/{id}")
    public ResponseEntity<IngressoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid IngressoInserirDTO dto) {
        IngressoDTO ingressoAtualizado = ingressoService.atualizar(id, dto);
        return ResponseEntity.ok(ingressoAtualizado);
    }

    @Operation(summary = "Deleta o ingresso pelo id", description = "Dado um determinado id, será deletado o ingresso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ingressoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id,
                                                @RequestBody AtualizarStatusIngressoDTO dto) {
        ingressoService.atualizarStatus(id, dto);
        return ResponseEntity.noContent().build();
    }
}
