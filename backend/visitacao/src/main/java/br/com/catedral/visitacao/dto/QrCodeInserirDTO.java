package br.com.catedral.visitacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

import br.com.catedral.visitacao.model.QrCode;
import br.com.catedral.visitacao.model.Ingresso;

public record QrCodeInserirDTO(
    
    @Size(max = 100)
    @NotBlank(message = "Tipo não pode ser nulo")
    String tipo,
    
    LocalDateTime dataGeracao,
    
    @NotNull(message = "Ingresso não pode ser nulo")
    Long idIngresso,
    
    byte[] dados
) {

    public static QrCodeInserirDTO toDto(QrCode qrCode) {
        return new QrCodeInserirDTO(
            qrCode.getTipo(),
            qrCode.getDataGeracao(),
            qrCode.getIngresso().getId(),
            qrCode.getDados()
        );
    }

    public QrCode toEntity(QrCode qrCode) {
        qrCode.setTipo(tipo);
        qrCode.setDataGeracao(dataGeracao);
        qrCode.setDados(dados);
        
        Ingresso ingresso = new Ingresso();
        ingresso.setId(this.idIngresso);
        qrCode.setIngresso(ingresso);
        
        return qrCode;
    }
}
