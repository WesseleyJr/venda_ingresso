package br.com.catedral.visitacao.controller;

import br.com.catedral.visitacao.dto.GuiaInserirDTO;
import br.com.catedral.visitacao.dto.GuiaDTO;
import br.com.catedral.visitacao.service.GuiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/guia")
public class GuiaController {

    @Autowired
    private GuiaService guiaService;

    @GetMapping
    public ResponseEntity<List<GuiaDTO>> listarTodos() {
        List<GuiaDTO> guias = guiaService.listarTodos();
        return ResponseEntity.ok(guias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuiaDTO> buscarPorId(@PathVariable Long id) {
        GuiaDTO guia = guiaService.buscarPorId(id);
        return ResponseEntity.ok(guia);
    }

    @PostMapping
    public ResponseEntity<GuiaDTO> inserir(@RequestBody @Valid GuiaInserirDTO guiaInserirDTO) {
        GuiaDTO guia = guiaService.inserir(guiaInserirDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(guia); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuiaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid GuiaInserirDTO guiaInserirDTO) {
        GuiaDTO guia = guiaService.atualizar(id, guiaInserirDTO);
        return ResponseEntity.ok(guia); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        guiaService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
