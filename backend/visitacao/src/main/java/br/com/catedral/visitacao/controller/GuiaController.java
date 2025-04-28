package br.com.catedral.visitacao.controller;

import br.com.catedral.visitacao.dto.GuiaInserirDTO;
import br.com.catedral.visitacao.dto.GuiaDTO;
import br.com.catedral.visitacao.service.GuiaService;
import io.swagger.v3.oas.annotations.Operation;

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

    
    @Operation(summary = "Retorna todas os guias", description = "Lista todos os guias")
    @GetMapping
    public ResponseEntity<List<GuiaDTO>> listarTodos() {
        List<GuiaDTO> guias = guiaService.listarTodos();
        return ResponseEntity.ok(guias);
    }

    
    @Operation(summary = "Retorna o guia pelo id", description = "Dado um determinado id, será retornado o guia")
    @GetMapping("/{id}")
    public ResponseEntity<GuiaDTO> buscarPorId(@PathVariable Long id) {
        GuiaDTO guia = guiaService.buscarPorId(id);
        return ResponseEntity.ok(guia);
    }

    @Operation(summary = "Cadastro do guia", description = "Cadastro do guia dado o determinado BODY")
    @PostMapping
    public ResponseEntity<GuiaDTO> inserir(@RequestBody @Valid GuiaInserirDTO guiaInserirDTO) {
        GuiaDTO guia = guiaService.inserir(guiaInserirDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(guia); 
    }

    @Operation(summary = "Atualiza o guia pelo id", description = "Dado um determinado id e as informações, será atualizado os dados de cadastro do guia")
    @PutMapping("/{id}")
    public ResponseEntity<GuiaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid GuiaInserirDTO guiaInserirDTO) {
        GuiaDTO guia = guiaService.atualizar(id, guiaInserirDTO);
        return ResponseEntity.ok(guia); 
    }

    @Operation(summary = "Deleta o guia pelo id", description = "Dado um determinado id, será deletado o guia")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        guiaService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
