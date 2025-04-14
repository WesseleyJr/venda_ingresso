package br.com.catedral.visitacao.controller;

import br.com.catedral.visitacao.dto.PagamentoDTO;
import br.com.catedral.visitacao.dto.PagamentoInserirDTO;
import br.com.catedral.visitacao.service.PagamentoService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoDTO> inserir(@Valid @RequestBody PagamentoInserirDTO pagamentoInserirDTO) {
        PagamentoDTO pagamentoDTO = pagamentoService.inserir(pagamentoInserirDTO);
        if (pagamentoDTO != null) {
            return new ResponseEntity<>(pagamentoDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> buscarTodos() {
        List<PagamentoDTO> pagamentos = pagamentoService.buscarTodos();
        return new ResponseEntity<>(pagamentos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> buscarPorId(@PathVariable Long id) {
        Optional<PagamentoDTO> pagamentoDTO = pagamentoService.buscarPorId(id);
        return pagamentoDTO.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PagamentoInserirDTO pagamentoInserirDTO) {
        Optional<PagamentoDTO> pagamentoDTO = pagamentoService.atualizar(id, pagamentoInserirDTO);
        return pagamentoDTO.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (pagamentoService.excluir(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
