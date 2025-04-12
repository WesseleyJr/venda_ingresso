package br.com.catedral.visitacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

import br.com.catedral.visitacao.model.QrCode;

public record QrCodeDTO(
    
    Long id,
    
    @Size(max = 100)
    @NotBlank(message = "Tipo não pode ser nulo")
    String tipo,
    
    LocalDateTime dataGeracao,
    
    @NotNull(message = "Ingresso não pode ser nulo")
    Long idIngresso,
    
    byte[] dados
) {

    public static QrCodeDTO toDto(QrCode qrCode) {
        return new QrCodeDTO(
            qrCode.getId(),
            qrCode.getTipo(),
            qrCode.getDataGeracao(),
            qrCode.getIngresso().getId(),
            qrCode.getDados()
        );
    }
}
