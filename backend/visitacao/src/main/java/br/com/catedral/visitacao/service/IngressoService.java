package br.com.catedral.visitacao.service;

import br.com.catedral.visitacao.constants.StatusIngressoEnum;
import br.com.catedral.visitacao.constants.StatusPagamentoEnum;
import br.com.catedral.visitacao.dto.IngressoDTO;
import br.com.catedral.visitacao.dto.IngressoInserirDTO;
import br.com.catedral.visitacao.dto.PagamentoDTO;
import br.com.catedral.visitacao.dto.PagamentoInserirDTO;
import br.com.catedral.visitacao.model.Agenda;
import br.com.catedral.visitacao.model.Ingresso;
import br.com.catedral.visitacao.model.Pagamento;
import br.com.catedral.visitacao.model.Usuario;
import br.com.catedral.visitacao.repository.AgendaRepository;
import br.com.catedral.visitacao.repository.IngressoRepository;
import br.com.catedral.visitacao.repository.PagamentoRepository;
import br.com.catedral.visitacao.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngressoService {

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;
    
    @Autowired
    private PagamentoService pagamentoService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

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
    public List<IngressoDTO> inserirLista(List<IngressoInserirDTO> dtos) {
        List<IngressoDTO> ingressosCriados = new ArrayList<>();
        
        double valor = 0;
        
        for (IngressoInserirDTO dto : dtos) {
        	Long idAgenda = dto.idAgenda();
        	Optional<Agenda> agendaOPT = agendaRepository.findById(idAgenda);
        	
        	valor += agendaOPT.get().getPreco();
        	
        }
        
        PagamentoInserirDTO pagamentoInserirDTO = new PagamentoInserirDTO(StatusPagamentoEnum.PENDENTE, valor, "PIX", null);
        
        PagamentoDTO pagamentoDTO = pagamentoService.inserir(pagamentoInserirDTO);
        
        

        for (IngressoInserirDTO dto : dtos) {
        	
        	dto = new IngressoInserirDTO(StatusIngressoEnum.PENDENTE, dto.idAgenda(), pagamentoDTO.id(), dto.nomeCompleto(), dto.celular(), dto.dataNascimento(), dto.nomeResponsavel(), dto.idUsuario());
        	
            Agenda agenda = agendaRepository.findById(dto.idAgenda())
                    .orElseThrow(() -> new EntityNotFoundException("Agenda não encontrada"));

            Pagamento pagamento = pagamentoRepository.findById(dto.idPagamento())
                    .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));

            Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

            Ingresso ingresso = dto.toEntity(agenda, pagamento, usuario);

            ingressoRepository.save(ingresso);
            ingressosCriados.add(IngressoDTO.toDto(ingresso));
        }

        return ingressosCriados;
    }

    @Transactional
    public IngressoDTO atualizar(Long id, IngressoInserirDTO dto) {
        Ingresso ingresso = ingressoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingresso não encontrado"));

        Agenda agenda = agendaRepository.findById(dto.idAgenda())
                .orElseThrow(() -> new EntityNotFoundException("Agenda não encontrada"));

        Pagamento pagamento = pagamentoRepository.findById(dto.idPagamento())
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));

        ingresso.setStatusIngressoEnum(dto.statusIngressoEnum());
        ingresso.setAgenda(agenda);
        ingresso.setPagamento(pagamento);
        ingressoRepository.save(ingresso);

        return IngressoDTO.toDto(ingresso);
    }

    public void deletar(Long id) {
        if (!ingressoRepository.existsById(id)) {
            throw new EntityNotFoundException("Ingresso não encontrado");
        }
        ingressoRepository.deleteById(id);
    }
}
