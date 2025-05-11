package br.com.catedral.visitacao.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.catedral.visitacao.constants.GeneroEnum;
import br.com.catedral.visitacao.constants.StatusIngressoEnum;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ingresso")
public class Ingresso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusIngressoEnum statusIngressoEnum;
	
	@ManyToOne
    @JoinColumn(name = "id_agenda", referencedColumnName = "id")
	@NotNull
	@JsonManagedReference
    private Agenda agenda;
	
	@ManyToOne
    @JoinColumn(name = "id_pagamento", referencedColumnName = "id")
	@NotNull
    private Pagamento pagamento;
	
	@NotBlank(message = "Nome completo não pode ser nulo")
	@Column(name = "nome_completo")
	private String nomeCompleto;
	
	@Size(max = 14)
	@NotBlank(message = "Celular não pode ser nulo")
	@Column(name = "celular", length = 14)
	private String celular;
	
	@NotNull(message = "Data de Nascimento não pode ser nulo")
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	
	@Column(name = "nome_responsavel")
	private String nomeResponsavel;
    
	@ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
	@NotNull
    private Usuario usuario;

	@OneToOne
	private QrCode qrCode;
	
	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public QrCode getQrCode() {
		return qrCode;
	}

	public void setQrCode(QrCode qrCode) {
		this.qrCode = qrCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatusIngressoEnum getStatusIngressoEnum() {
		return statusIngressoEnum;
	}

	public void setStatusIngressoEnum(StatusIngressoEnum statusIngressoEnum) {
		this.statusIngressoEnum = statusIngressoEnum;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
