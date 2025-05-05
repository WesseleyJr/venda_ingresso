package br.com.catedral.visitacao.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import br.com.catedral.visitacao.constants.StatusPagamentoEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pagamento")
public class Pagamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPagamentoEnum statusPagamentoEnum;
    
	@NotNull(message = "Valor não pode ser nulo")
	@Column(name = "valor")
	private Double valor;
	
	@Size(max = 50)
	@NotBlank(message = "Metodo pagamento não pode ser nulo")
	@Column(name = "metodo_pagamento", length = 50)
	private String metodoPagamento;
	
	@Column(name = "data_pagamento")
	private LocalDateTime dataPagamento;
	
	
	@OneToMany(mappedBy = "pagamento", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Ingresso> ingressos = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatusPagamentoEnum getStatusPagamentoEnum() {
		return statusPagamentoEnum;
	}

	public void setStatusPagamentoEnum(StatusPagamentoEnum statusPagamentoEnum) {
		this.statusPagamentoEnum = statusPagamentoEnum;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}

	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Set<Ingresso> getIngressos() {
		return ingressos;
	}

	public void setIngressos(Set<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}
	
	

}
