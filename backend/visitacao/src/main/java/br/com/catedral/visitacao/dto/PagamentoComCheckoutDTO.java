package br.com.catedral.visitacao.dto;

public class PagamentoComCheckoutDTO {
    private PagamentoDTO pagamento;
    private String checkoutUrl;

    public PagamentoComCheckoutDTO(PagamentoDTO pagamento, String checkoutUrl) {
        this.pagamento = pagamento;
        this.checkoutUrl = checkoutUrl;
    }

	public PagamentoDTO getPagamento() {
		return pagamento;
	}

	public void setPagamento(PagamentoDTO pagamento) {
		this.pagamento = pagamento;
	}

	public String getCheckoutUrl() {
		return checkoutUrl;
	}

	public void setCheckoutUrl(String checkoutUrl) {
		this.checkoutUrl = checkoutUrl;
	}
    
    
}
