package br.com.catedral.visitacao.dto;

import br.com.catedral.visitacao.constants.StatusPagamentoEnum;
import br.com.catedral.visitacao.model.Pagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record PagamentoDTO(
    
    Long id,
    
    @NotNull(message = "Status do pagamento não pode ser nulo")
    StatusPagamentoEnum statusPagamentoEnum,
    
    @NotNull(message = "Valor não pode ser nulo")
    Double valor,
    
    @Size(max = 50)
    @NotBlank(message = "Método de pagamento não pode ser nulo")
    String metodoPagamento,
    
    LocalDateTime dataPagamento
    
) {

    public static PagamentoDTO toDto(Pagamento pagamento) {
        return new PagamentoDTO(
            pagamento.getId(),
            pagamento.getStatusPagamentoEnum(),
            pagamento.getValor(),
            pagamento.getMetodoPagamento(),
            pagamento.getDataPagamento()
        );
    }
    
    public static Pagamento toEntity(PagamentoDTO dto) {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(dto.id());
        pagamento.setStatusPagamentoEnum(dto.statusPagamentoEnum());
        pagamento.setValor(dto.valor());
        pagamento.setMetodoPagamento(dto.metodoPagamento());
        pagamento.setDataPagamento(dto.dataPagamento());
        return pagamento;
    }
}
