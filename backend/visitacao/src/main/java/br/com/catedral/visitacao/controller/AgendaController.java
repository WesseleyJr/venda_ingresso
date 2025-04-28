package br.com.catedral.visitacao.controller;

import br.com.catedral.visitacao.dto.AgendaDTO;
import br.com.catedral.visitacao.dto.AgendaInserirDTO;
import br.com.catedral.visitacao.service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;

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

	@Operation(summary = "Cadastro da agenda", description = "O colaborador cria a agenda disponibilizando esse horario para o cliente comprar")
    @PostMapping
    public ResponseEntity<AgendaDTO> criarAgenda(@RequestBody AgendaInserirDTO agendaInserirDTO) {
        AgendaDTO agendaDTO = agendaService.inserir(agendaInserirDTO);
        if (agendaDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaDTO);
    }
	
	@Operation(summary = "Retorna todas as agendas", description = "Lista todas as agendas")
    @GetMapping
    public ResponseEntity<List<AgendaDTO>> buscarTodasAgendas() {
        List<AgendaDTO> agendas = agendaService.buscarTodas();
        return ResponseEntity.ok(agendas);
    }
	
	@Operation(summary = "Retorna todas as agendas pelo mes", description = "Lista todas as agendas disponiveis no mes")
	@GetMapping("/mes/{mes}")
	public ResponseEntity<List<AgendaDTO>> buscarPorMes(@PathVariable int mes) {
		List<AgendaDTO> agendas = agendaService.buscarPorMes(mes);
		return ResponseEntity.ok(agendas);
	}

	@Operation(summary = "Retorna todas as agendas pelo mes e dia", description = "Lista todas as agendas disponiveis no mes e dia")
	@GetMapping("/mes/dia/{mes}/{dia}")
	public ResponseEntity<List<AgendaDTO>> buscarPorMes(@PathVariable int mes, @PathVariable int dia) {
		List<AgendaDTO> agendas = agendaService.buscarPorMesDia(mes, dia);
		return ResponseEntity.ok(agendas);
	}
	
	@Operation(summary = "Retorna a agenda pelo id", description = "Dado um determinado id, será retornado a agenda")
    @GetMapping("/{id}")
    public ResponseEntity<AgendaDTO> buscarAgendaPorId(@PathVariable Long id) {
        Optional<AgendaDTO> agendaDTO = agendaService.buscarPorId(id);
        return agendaDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

	@Operation(summary = "Atualiza a agenda pelo id", description = "Dado um determinado id, e as informações será atualizado os dados de cadastro da agenda")
    @PutMapping("/{id}")
    public ResponseEntity<AgendaDTO> atualizarAgenda(@PathVariable Long id, @RequestBody AgendaInserirDTO agendaInserirDTO) {
        Optional<AgendaDTO> agendaDTO = agendaService.atualizar(id, agendaInserirDTO);
        return agendaDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
	
	@Operation(summary = "Deleta a agenda pelo id", description = "Dado um determinado id, será deletado a agenda")
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
