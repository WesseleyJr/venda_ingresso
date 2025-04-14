package br.com.catedral.visitacao.model;

import java.util.HashSet;
import java.util.Set;

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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

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
    private Agenda agenda;
	
	@ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
	@NotNull
    private Cliente cliente;
	
	@OneToMany(mappedBy = "ingresso", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Pagamento> pagamentos = new HashSet<>();

	@OneToMany(mappedBy = "ingresso", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<QrCode> qrCodes = new HashSet<>();
	
	public Set<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(Set<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public Set<QrCode> getQrCodes() {
		return qrCodes;
	}

	public void setQrCodes(Set<QrCode> qrCodes) {
		this.qrCodes = qrCodes;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
}
