package br.com.catedral.visitacao.service;

import br.com.catedral.visitacao.dto.QrCodeDTO;
import br.com.catedral.visitacao.dto.QrCodeInserirDTO;
import br.com.catedral.visitacao.model.QrCode;
import br.com.catedral.visitacao.model.Ingresso;
import br.com.catedral.visitacao.repository.QrCodeRepository;
import br.com.catedral.visitacao.repository.IngressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QrCodeService {

    @Autowired
    private QrCodeRepository qrCodeRepository;

    @Autowired
    private IngressoRepository ingressoRepository;

    public QrCodeDTO inserir(QrCodeInserirDTO qrCodeInserirDTO) {
        Optional<Ingresso> ingressoOptional = ingressoRepository.findById(qrCodeInserirDTO.idIngresso());

        if (ingressoOptional.isEmpty()) {
            return null;
        }

        Ingresso ingresso = ingressoOptional.get();

        QrCode qrCode = qrCodeInserirDTO.toEntity(new QrCode());
        qrCode.setIngresso(ingresso);

        QrCode qrCodeSalvo = qrCodeRepository.save(qrCode);

        return QrCodeDTO.toDto(qrCodeSalvo); 
    }

    public List<QrCodeDTO> buscarTodos() {
        List<QrCode> qrCodes = qrCodeRepository.findAll();
        return qrCodes.stream()
                      .map(QrCodeDTO::toDto)
                      .collect(Collectors.toList());
    }

    public Optional<QrCodeDTO> buscarPorId(Long id) {
        Optional<QrCode> qrCode = qrCodeRepository.findById(id);
        return qrCode.map(QrCodeDTO::toDto);
    }

    public Optional<QrCodeDTO> atualizar(Long id, QrCodeInserirDTO qrCodeInserirDTO) {
        Optional<QrCode> qrCodeOptional = qrCodeRepository.findById(id);

        if (qrCodeOptional.isPresent()) {
            QrCode qrCode = qrCodeOptional.get();

            qrCodeInserirDTO.toEntity(qrCode);

            Optional<Ingresso> ingressoOptional = ingressoRepository.findById(qrCodeInserirDTO.idIngresso());
            if (ingressoOptional.isPresent()) {
                Ingresso ingresso = ingressoOptional.get();
                qrCode.setIngresso(ingresso);
            }

            QrCode qrCodeAtualizado = qrCodeRepository.save(qrCode);

            return Optional.of(QrCodeDTO.toDto(qrCodeAtualizado));
        }

        return Optional.empty();
    }

    public boolean excluir(Long id) {
        Optional<QrCode> qrCodeOptional = qrCodeRepository.findById(id);

        if (qrCodeOptional.isPresent()) {
            qrCodeRepository.deleteById(id);
            return true;
        }

        return false; 
    }
}
