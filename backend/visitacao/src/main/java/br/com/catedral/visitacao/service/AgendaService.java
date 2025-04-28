package br.com.catedral.visitacao.service;

import br.com.catedral.visitacao.dto.AgendaDTO;
import br.com.catedral.visitacao.dto.AgendaInserirDTO;
import br.com.catedral.visitacao.model.Agenda;
import br.com.catedral.visitacao.model.Guia;
import br.com.catedral.visitacao.repository.AgendaRepository;
import br.com.catedral.visitacao.repository.GuiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private GuiaRepository guiaRepository;
    
    public List<AgendaDTO> buscarPorMes(int mes) {
         List<Agenda> agendas = agendaRepository.buscarAgendaPorMes(mes);
         return agendas.stream()
                 .map(AgendaDTO::toDto)
                 .collect(Collectors.toList());
    }
    
    public List<AgendaDTO> buscarPorMesDia(int mes, int dia) {
    	List<Agenda> agendas = agendaRepository.buscarAgendaPorMesEDia(mes, dia);
    	return agendas.stream()
    			.map(AgendaDTO::toDto)
    			.collect(Collectors.toList());
    }

    public AgendaDTO inserir(AgendaInserirDTO agendaInserirDTO) {
        Optional<Guia> guiaOptional = guiaRepository.findById(agendaInserirDTO.idGuia());

        if (guiaOptional.isEmpty()) {
            return null;  
        }

        Guia guia = guiaOptional.get();

        Agenda agenda = agendaInserirDTO.toEntity(new Agenda());
        agenda.setGuia(guia);

        Agenda agendaSalva = agendaRepository.save(agenda);

        return AgendaDTO.toDto(agendaSalva);
    }

    public List<AgendaDTO> buscarTodas() {
        List<Agenda> agendas = agendaRepository.findAll();
        return agendas.stream()
                      .map(AgendaDTO::toDto)
                      .collect(Collectors.toList());
    }

    public Optional<AgendaDTO> buscarPorId(Long id) {
        Optional<Agenda> agenda = agendaRepository.findById(id);
        return agenda.map(AgendaDTO::toDto);
    }

    public Optional<AgendaDTO> atualizar(Long id, AgendaInserirDTO agendaInserirDTO) {
        Optional<Agenda> agendaOptional = agendaRepository.findById(id);

        if (agendaOptional.isPresent()) {
            Agenda agenda = agendaOptional.get();

            agendaInserirDTO.toEntity(agenda);

            Optional<Guia> guiaOptional = guiaRepository.findById(agendaInserirDTO.idGuia());
            if (guiaOptional.isPresent()) {
                Guia guia = guiaOptional.get();
                agenda.setGuia(guia);
            }

            Agenda agendaAtualizada = agendaRepository.save(agenda);

            return Optional.of(AgendaDTO.toDto(agendaAtualizada));
        }

        return Optional.empty();
    }

    public boolean excluir(Long id) {
        Optional<Agenda> agendaOptional = agendaRepository.findById(id);

        if (agendaOptional.isPresent()) {
            agendaRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
