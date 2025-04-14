package br.com.catedral.visitacao.service;

import br.com.catedral.visitacao.dto.IngressoDTO;
import br.com.catedral.visitacao.dto.IngressoInserirDTO;
import br.com.catedral.visitacao.model.Agenda;
import br.com.catedral.visitacao.model.Cliente;
import br.com.catedral.visitacao.model.Ingresso;
import br.com.catedral.visitacao.repository.AgendaRepository;
import br.com.catedral.visitacao.repository.ClienteRepository;
import br.com.catedral.visitacao.repository.IngressoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngressoService {

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<IngressoDTO> listarTodos() {
        return ingressoRepository.findAll()
                .stream()
                .map(IngressoDTO::toDto)
                .collect(Collectors.toList());
    }

    public IngressoDTO buscarPorId(Long id) {
        Ingresso ingresso = ingressoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingresso não encontrado"));
        return IngressoDTO.toDto(ingresso);
    }

    @Transactional
    public IngressoDTO inserir(IngressoInserirDTO dto) {
        Agenda agenda = agendaRepository.findById(dto.idAgenda())
                .orElseThrow(() -> new EntityNotFoundException("Agenda não encontrada"));

        Cliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        Ingresso ingresso = dto.toEntity(agenda, cliente);
        ingressoRepository.save(ingresso);

        return IngressoDTO.toDto(ingresso);
    }

    @Transactional
    public IngressoDTO atualizar(Long id, IngressoInserirDTO dto) {
        Ingresso ingresso = ingressoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingresso não encontrado"));

        Agenda agenda = agendaRepository.findById(dto.idAgenda())
                .orElseThrow(() -> new EntityNotFoundException("Agenda não encontrada"));

        Cliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        ingresso.setStatusIngressoEnum(dto.statusIngressoEnum());
        ingresso.setAgenda(agenda);
        ingresso.setCliente(cliente);

        ingressoRepository.save(ingresso);

        return IngressoDTO.toDto(ingresso);
    }

    // Deletar ingresso
    public void deletar(Long id) {
        if (!ingressoRepository.existsById(id)) {
            throw new EntityNotFoundException("Ingresso não encontrado");
        }
        ingressoRepository.deleteById(id);
    }
}
