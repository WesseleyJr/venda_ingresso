package br.com.catedral.visitacao.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.catedral.visitacao.constants.GeneroEnum;
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
@Table(name = "cliente")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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
	
    @NotNull
    @Enumerated(EnumType.STRING)
    private GeneroEnum genero;
    
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Ingresso> ingressos = new HashSet<>();
    
	@ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
	@NotNull
    private Usuario usuario;

	
	
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

	public GeneroEnum getGenero() {
		return genero;
	}

	public void setGenero(GeneroEnum genero) {
		this.genero = genero;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
    
}
