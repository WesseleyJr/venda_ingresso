package br.com.catedral.visitacao.controller;

import br.com.catedral.visitacao.dto.PagamentoComCheckoutDTO;
import br.com.catedral.visitacao.dto.PagamentoDTO;
import br.com.catedral.visitacao.dto.PagamentoInserirDTO;
import br.com.catedral.visitacao.dto.PagamentoStatusDTO;
import br.com.catedral.visitacao.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @Operation(summary = "Cadastro do pagamento", description = "Cadastro do pagamento dado o determinado BODY")
    @PostMapping
    public ResponseEntity<PagamentoComCheckoutDTO> inserir(@Valid @RequestBody PagamentoInserirDTO pagamentoInserirDTO) {
        try {
            PagamentoComCheckoutDTO pagamentoComCheckoutDTO = pagamentoService.inserir(pagamentoInserirDTO);
            if (pagamentoComCheckoutDTO != null) {
                return new ResponseEntity<>(pagamentoComCheckoutDTO, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Retorna todas os pagamentos", description = "Lista todos os pagamentos")
    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> buscarTodos() {
        List<PagamentoDTO> pagamentos = pagamentoService.buscarTodos();
        return new ResponseEntity<>(pagamentos, HttpStatus.OK);
    }

    @Operation(summary = "Retorna o pagamento pelo id", description = "Dado um determinado id, será retornado o pagamento")
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> buscarPorId(@PathVariable Long id) {
        Optional<PagamentoDTO> pagamentoDTO = pagamentoService.buscarPorId(id);
        return pagamentoDTO.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Atualiza o pagamento pelo id", description = "Dado um determinado id e as informações, será atualizado os dados de cadastro do pagamento")
    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PagamentoInserirDTO pagamentoInserirDTO) {
        Optional<PagamentoDTO> pagamentoDTO = pagamentoService.atualizar(id, pagamentoInserirDTO);
        return pagamentoDTO.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Deleta o pagamento pelo id", description = "Dado um determinado id, será deletado o pagamento")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (pagamentoService.excluir(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @Operation(summary = "Atualiza o status do pagamento pelo id", description = "Atualiza apenas o status de um pagamento existente.")
    @PatchMapping("/{id}/status")
    public ResponseEntity<PagamentoDTO> atualizarStatus(@PathVariable Long id, @Valid @RequestBody PagamentoStatusDTO pagamentoStatusDTO) {
        Optional<PagamentoDTO> pagamentoDTO = pagamentoService.atualizarStatus(id, pagamentoStatusDTO);

        return pagamentoDTO.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
