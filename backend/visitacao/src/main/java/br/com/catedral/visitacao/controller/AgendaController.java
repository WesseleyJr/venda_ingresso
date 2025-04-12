package br.com.catedral.visitacao.controller;

import br.com.catedral.visitacao.dto.AgendaDTO;
import br.com.catedral.visitacao.dto.AgendaInserirDTO;
import br.com.catedral.visitacao.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @PostMapping
    public ResponseEntity<AgendaDTO> criarAgenda(@RequestBody AgendaInserirDTO agendaInserirDTO) {
        AgendaDTO agendaDTO = agendaService.inserir(agendaInserirDTO);
        if (agendaDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaDTO);
    }

    @GetMapping
    public ResponseEntity<List<AgendaDTO>> buscarTodasAgendas() {
        List<AgendaDTO> agendas = agendaService.buscarTodas();
        return ResponseEntity.ok(agendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaDTO> buscarAgendaPorId(@PathVariable Long id) {
        Optional<AgendaDTO> agendaDTO = agendaService.buscarPorId(id);
        return agendaDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendaDTO> atualizarAgenda(@PathVariable Long id, @RequestBody AgendaInserirDTO agendaInserirDTO) {
        Optional<AgendaDTO> agendaDTO = agendaService.atualizar(id, agendaInserirDTO);
        return agendaDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAgenda(@PathVariable Long id) {
        boolean excluido = agendaService.excluir(id);
        if (excluido) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
