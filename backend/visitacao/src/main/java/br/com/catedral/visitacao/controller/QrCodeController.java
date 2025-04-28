package br.com.catedral.visitacao.controller;

import br.com.catedral.visitacao.dto.QrCodeDTO;
import br.com.catedral.visitacao.dto.QrCodeInserirDTO;
import br.com.catedral.visitacao.service.QrCodeService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    @Autowired
    private QrCodeService qrCodeService;

    @Operation(summary = "Cadastro do qrcode", description = "Cadastro do qrcode dado o determinado BODY")
    @PostMapping
    public ResponseEntity<QrCodeDTO> inserir(@RequestBody QrCodeInserirDTO qrCodeInserirDTO) {
        QrCodeDTO qrCodeDTO = qrCodeService.inserir(qrCodeInserirDTO);
        
        if (qrCodeDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(qrCodeDTO);
    }

    @Operation(summary = "Retorna todas os qrcodes", description = "Lista todos os qrcodes")
    @GetMapping
    public ResponseEntity<List<QrCodeDTO>> buscarTodos() {
        List<QrCodeDTO> qrCodes = qrCodeService.buscarTodos();
        return ResponseEntity.ok(qrCodes);
    }

    @Operation(summary = "Retorna o qrcode pelo id", description = "Dado um determinado id, será retornado o qrcode")
    @GetMapping("/{id}")
    public ResponseEntity<QrCodeDTO> buscarPorId(@PathVariable Long id) {
        Optional<QrCodeDTO> qrCodeDTO = qrCodeService.buscarPorId(id);
        
        if (qrCodeDTO.isPresent()) {
            return ResponseEntity.ok(qrCodeDTO.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  
        }
    }

    @Operation(summary = "Atualiza o qrcode pelo id", description = "Dado um determinado id e as informações, será atualizado os dados de cadastro do qrcode")
    @PutMapping("/{id}")
    public ResponseEntity<QrCodeDTO> atualizar(@PathVariable Long id, @RequestBody QrCodeInserirDTO qrCodeInserirDTO) {
        Optional<QrCodeDTO> qrCodeDTO = qrCodeService.atualizar(id, qrCodeInserirDTO);
        
        if (qrCodeDTO.isPresent()) {
            return ResponseEntity.ok(qrCodeDTO.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  
        }
    }

    @Operation(summary = "Deleta o qrcode pelo id", description = "Dado um determinado id, será deletado o qrcode")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean isDeleted = qrCodeService.excluir(id);
        
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  
        }
    }
}
