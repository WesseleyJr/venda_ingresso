package br.com.catedral.visitacao.model;

import java.sql.Types;
import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "qrcode")
public class QrCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(max = 100)
	@NotBlank(message = "Tipo não pode ser nulo")
	@Column(name = "tipo", length = 100)
	private String tipo;
	
	@Column(name = "data_geracao")
	private LocalDateTime dataGeracao;
	
	@OneToOne
    @JoinColumn(name = "id_ingresso", referencedColumnName = "id")
	@NotNull
    private Ingresso ingresso;
	
	@Lob
	@JdbcTypeCode(Types.BINARY)
	private byte[] dados;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getDataGeracao() {
		return dataGeracao;
	}

	public void setDataGeracao(LocalDateTime dataGeracao) {
		this.dataGeracao = dataGeracao;
	}

	public Ingresso getIngresso() {
		return ingresso;
	}

	public void setIngresso(Ingresso ingresso) {
		this.ingresso = ingresso;
	}

	public byte[] getDados() {
		return dados;
	}

	public void setDados(byte[] dados) {
		this.dados = dados;
	}
	
	
}
