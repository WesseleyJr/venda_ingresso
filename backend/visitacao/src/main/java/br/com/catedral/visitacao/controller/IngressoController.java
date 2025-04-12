package br.com.catedral.visitacao.controller;

import br.com.catedral.visitacao.dto.IngressoDTO;
import br.com.catedral.visitacao.dto.IngressoInserirDTO;
import br.com.catedral.visitacao.service.IngressoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingresso")
public class IngressoController {

    @Autowired
    private IngressoService ingressoService;

    @GetMapping
    public ResponseEntity<List<IngressoDTO>> listarTodos() {
        return ResponseEntity.ok(ingressoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngressoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ingressoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<IngressoDTO> inserir(@RequestBody @Valid IngressoInserirDTO dto) {
        IngressoDTO ingressoCriado = ingressoService.inserir(dto);
        return ResponseEntity.ok(ingressoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngressoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid IngressoInserirDTO dto) {
        IngressoDTO ingressoAtualizado = ingressoService.atualizar(id, dto);
        return ResponseEntity.ok(ingressoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ingressoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
