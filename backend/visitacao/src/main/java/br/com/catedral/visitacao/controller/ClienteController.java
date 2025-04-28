package br.com.catedral.visitacao.controller;

import br.com.catedral.visitacao.dto.ClienteDTO;
import br.com.catedral.visitacao.dto.ClienteInserirDTO;
import br.com.catedral.visitacao.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Cadastro de clientes", description = "Cadastro de uma lista de clientes dado o determinado BODY")
    @PostMapping("/lista")
    public ResponseEntity<List<ClienteDTO>> inserir(@RequestBody List<ClienteInserirDTO> clientesInserirDTO) {
        List<ClienteDTO> clientesDTO = clienteService.inserirLista(clientesInserirDTO);
        
        if (!clientesDTO.isEmpty()) {
            return ResponseEntity.ok(clientesDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(summary = "Cadastro do cliente", description = "Cadastro do cliente dado o determinado BODY")
    @PostMapping
    public ResponseEntity<ClienteDTO> inserir(@RequestBody @Valid ClienteInserirDTO clienteInserirDTO) {
        ClienteDTO clienteDTO = clienteService.inserir(clienteInserirDTO);
        if (clienteDTO != null) {
            return ResponseEntity.ok(clienteDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(summary = "Retorna todas os clientes", description = "Lista todos os clientes")
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> buscarTodos() {
        List<ClienteDTO> clientes = clienteService.buscarTodos();
        return ResponseEntity.ok(clientes);
    }
    
    @Operation(summary = "Retorna o cliente pelo id", description = "Dado um determinado id, será retornado o cliente")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        Optional<ClienteDTO> clienteOptional = clienteService.buscarPorId(id);
        return clienteOptional.map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualiza o cliente pelo id", description = "Dado um determinado id, e as informações será atualizado os dados de cadastro do cliente")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteInserirDTO clienteInserirDTO) {
        Optional<ClienteDTO> clienteAtualizado = clienteService.atualizar(id, clienteInserirDTO);
        return clienteAtualizado.map(ResponseEntity::ok)
                                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deleta o cliente pelo id", description = "Dado um determinado id, será deletado o cliente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean removido = clienteService.excluir(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
