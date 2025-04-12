package br.com.catedral.visitacao.service;

import br.com.catedral.visitacao.dto.PagamentoDTO;
import br.com.catedral.visitacao.dto.PagamentoInserirDTO;
import br.com.catedral.visitacao.model.Ingresso;
import br.com.catedral.visitacao.model.Pagamento;
import br.com.catedral.visitacao.repository.IngressoRepository;
import br.com.catedral.visitacao.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private IngressoRepository ingressoRepository;

    public PagamentoDTO inserir(PagamentoInserirDTO dto) {
        Optional<Ingresso> ingressoOptional = ingressoRepository.findById(dto.idIngresso());

        if (ingressoOptional.isEmpty()) {
            return null;
        }

        Pagamento pagamento = dto.toEntity();
        pagamento.setIngresso(ingressoOptional.get());

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

        Optional<Ingresso> ingressoOptional = ingressoRepository.findById(dto.idIngresso());
        ingressoOptional.ifPresent(pagamento::setIngresso);

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
}
