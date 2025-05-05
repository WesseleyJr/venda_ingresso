package br.com.catedral.visitacao.service;

import br.com.catedral.visitacao.constants.StatusIngressoEnum;
import br.com.catedral.visitacao.constants.StatusPagamentoEnum;
import br.com.catedral.visitacao.dto.PagamentoDTO;
import br.com.catedral.visitacao.dto.PagamentoInserirDTO;
import br.com.catedral.visitacao.dto.PagamentoStatusDTO;
import br.com.catedral.visitacao.dto.QrCodeDTO;
import br.com.catedral.visitacao.dto.QrCodeInserirDTO;
import br.com.catedral.visitacao.model.Ingresso;
import br.com.catedral.visitacao.model.Pagamento;
import br.com.catedral.visitacao.repository.IngressoRepository;
import br.com.catedral.visitacao.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private IngressoRepository ingressoRepository;
    
    @Autowired
    private QrCodeService qrCodeService;

    @Autowired
    private GeradorPdfService geradorPdfService;
    
    @Autowired
    private EmailService emailService;

    public PagamentoDTO inserir(PagamentoInserirDTO dto) {

        Pagamento pagamento = dto.toEntity();

        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);

        return PagamentoDTO.toDto(pagamentoSalvo);
    }

    public List<PagamentoDTO> buscarTodos() {
        return pagamentoRepository.findAll()
                .stream()
                .map(PagamentoDTO::toDto)
                .collect(Collectors.toList());
    }

    public Optional<PagamentoDTO> buscarPorId(Long id) {
        return pagamentoRepository.findById(id).map(PagamentoDTO::toDto);
    }

    public Optional<PagamentoDTO> atualizar(Long id, PagamentoInserirDTO dto) {
        Optional<Pagamento> pagamentoOptional = pagamentoRepository.findById(id);

        if (pagamentoOptional.isEmpty()) {
            return Optional.empty();
        }

        Pagamento pagamento = pagamentoOptional.get();

        pagamento.setStatusPagamentoEnum(dto.statusPagamentoEnum());
        pagamento.setValor(dto.valor());
        pagamento.setMetodoPagamento(dto.metodoPagamento());
        pagamento.setDataPagamento(dto.dataPagamento());

        Pagamento atualizado = pagamentoRepository.save(pagamento);

        return Optional.of(PagamentoDTO.toDto(atualizado));
    }

    public boolean excluir(Long id) {
        if (pagamentoRepository.existsById(id)) {
            pagamentoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Optional<PagamentoDTO> atualizarStatus(Long id, PagamentoStatusDTO dto) {
        Optional<Pagamento> pagamentoOptional = pagamentoRepository.findById(id);

        if (pagamentoOptional.isEmpty()) {
            return Optional.empty();
        }

        Pagamento pagamento = pagamentoOptional.get();

        pagamento.setStatusPagamentoEnum(dto.statusPagamentoEnum());

        Pagamento atualizado = pagamentoRepository.save(pagamento);

        Set<Ingresso> ingressos = pagamento.getIngressos();

        List<QrCodeDTO> listaQrCodes = new ArrayList<>();

        for (Ingresso ingresso : ingressos) {
            if (atualizado.getStatusPagamentoEnum() == StatusPagamentoEnum.PENDENTE) {
                ingresso.setStatusIngressoEnum(StatusIngressoEnum.PENDENTE);
            } else if (atualizado.getStatusPagamentoEnum() == StatusPagamentoEnum.PAGO) {
                ingresso.setStatusIngressoEnum(StatusIngressoEnum.ATIVO);

                QrCodeInserirDTO qrCodeInserirDTO = new QrCodeInserirDTO("INGRESSO-TORRE", LocalDateTime.now(), ingresso.getId(), null);

                QrCodeDTO qrCodeDTO = qrCodeService.inserir(qrCodeInserirDTO);
                if (qrCodeDTO != null) {
                    listaQrCodes.add(qrCodeDTO);
                }

            } else if (atualizado.getStatusPagamentoEnum() == StatusPagamentoEnum.CANCELADO) {
                ingresso.setStatusIngressoEnum(StatusIngressoEnum.CANCELADO);
            }
            ingressoRepository.save(ingresso);
        }

        if (listaQrCodes.size() > 0) {
            try {
                byte[] pdfData = geradorPdfService.gerarPdfComQrCodes(new ArrayList<>(ingressos), listaQrCodes);
                
                String emailDestino = pagamento.getIngressos().iterator().next().getUsuario().getEmail();
                String assunto = "Ingressos Ativados - Catedral São Pedro de Alcântara";
                emailService.enviarEmailComPdf(emailDestino, assunto, pdfData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Optional.of(PagamentoDTO.toDto(atualizado));
    }
    
}
