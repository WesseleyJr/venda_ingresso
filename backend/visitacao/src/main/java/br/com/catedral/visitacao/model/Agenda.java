package br.com.catedral.visitacao.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "agenda")
public class Agenda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Data e Hora não pode ser nulo")
	@Column(name = "data_hora")
	private LocalDateTime dataHora;
	
	@NotNull(message = "Preço não pode ser nulo")
	@Column(name = "preco")
	private Double preco;
	
	@NotNull(message = "Capacidade não pode ser nulo")
	@Column(name = "capacidade")
	private Integer capacidade;
	
	@ManyToOne
    @JoinColumn(name = "id_guia", referencedColumnName = "id")
	@NotNull
    private Guia guia;
	
	@OneToMany(mappedBy = "agenda", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Ingresso> ingressos = new HashSet<>();

	
	
	public Set<Ingresso> getIngressos() {
		return ingressos;
	}

	public void setIngressos(Set<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}

	public Guia getGuia() {
		return guia;
	}

	public void setGuia(Guia guia) {
		this.guia = guia;
	}
	
	
}
