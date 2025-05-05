package br.com.catedral.visitacao.dto;

import br.com.catedral.visitacao.constants.StatusPagamentoEnum;
import br.com.catedral.visitacao.model.Ingresso;
import br.com.catedral.visitacao.model.Pagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record PagamentoInserirDTO(
	    
	    @NotNull(message = "Status do pagamento não pode ser nulo")
	    StatusPagamentoEnum statusPagamentoEnum,
	    
	    @NotNull(message = "Valor não pode ser nulo")
	    Double valor,
	    
	    @Size(max = 50)
	    @NotBlank(message = "Método de pagamento não pode ser nulo")
	    String metodoPagamento,
	    
	    LocalDateTime dataPagamento
	    


	) {

	    public static PagamentoInserirDTO toDto(Pagamento pagamento) {
	        return new PagamentoInserirDTO(
	            pagamento.getStatusPagamentoEnum(),
	            pagamento.getValor(),
	            pagamento.getMetodoPagamento(),
	            pagamento.getDataPagamento()
	        );
	    }

	    public Pagamento toEntity() {
	        Pagamento pagamento = new Pagamento();
	        pagamento.setStatusPagamentoEnum(statusPagamentoEnum);
	        pagamento.setValor(valor);
	        pagamento.setMetodoPagamento(metodoPagamento);
	        pagamento.setDataPagamento(dataPagamento);

	        return pagamento;
	    }
	}