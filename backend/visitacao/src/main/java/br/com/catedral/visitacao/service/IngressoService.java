package br.com.catedral.visitacao.service;

import br.com.catedral.visitacao.constants.StatusPagamentoEnum;
import br.com.catedral.visitacao.dto.AtualizarStatusIngressoDTO;
import br.com.catedral.visitacao.dto.IngressoDTO;
import br.com.catedral.visitacao.dto.IngressoInserirDTO;
import br.com.catedral.visitacao.dto.IngressoPagamentoPixDTO;
import br.com.catedral.visitacao.dto.PagamentoDTO;
import br.com.catedral.visitacao.dto.PagamentoInserirDTO;
import br.com.catedral.visitacao.dto.PixPaymentRequestDTO;
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
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    private PixPaymentService pixPaymentService;
    
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

    public Mono<IngressoPagamentoPixDTO> inserirLista(List<IngressoInserirDTO> dtos) {
        if (dtos.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Lista de ingressos vazia"));
        }

        Long idAgenda = dtos.get(0).idAgenda();
        Long idUsuario = dtos.get(0).idUsuario();

        Agenda agenda = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new EntityNotFoundException("Agenda não encontrada"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        double valor = agenda.getPreco() * dtos.size();

        PagamentoInserirDTO pagamentoInserirDTO = new PagamentoInserirDTO(StatusPagamentoEnum.PENDENTE, valor, "PIX", null);
        PagamentoDTO pagamentoDTO = pagamentoService.inserir(pagamentoInserirDTO);
        Pagamento pagamento = PagamentoDTO.toEntity(pagamentoDTO);


        PixPaymentRequestDTO pixPaymentRequestDTO = new PixPaymentRequestDTO();
        pixPaymentRequestDTO.setQuantity(dtos.size());
        pixPaymentRequestDTO.setTitle("Visitação da torre - Catedral São Pedro de Alcântara");
        pixPaymentRequestDTO.setUnitPrice(valor);

        return pixPaymentService.createPixPayment(pixPaymentRequestDTO, usuario.getEmail(), usuario.getCpf(), pagamentoDTO.id())
                .map(pixResponse -> {
                    List<IngressoDTO> ingressosCriados = new ArrayList<>();

                    for (IngressoInserirDTO dto : dtos) {

                        Ingresso ingresso = dto.toEntity(agenda, pagamento, usuario);

                        ingressoRepository.save(ingresso);
                        ingressosCriados.add(IngressoDTO.toDto(ingresso));
                    }

                    return new IngressoPagamentoPixDTO(ingressosCriados, pixResponse);
                });
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
    

    public void atualizarStatus(Long id, AtualizarStatusIngressoDTO dto) {
        Ingresso ingresso = ingressoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Ingresso não encontrado com ID: " + id));

        ingresso.setStatusIngressoEnum(dto.getStatus());
        ingressoRepository.save(ingresso);
    }
}
