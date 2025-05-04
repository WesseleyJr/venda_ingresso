package br.com.catedral.visitacao.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class EsqueceuSenhaToken {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String token;

	    @OneToOne
	    @JoinColumn(name = "usuario_id", nullable = false)
	    private Usuario usuario;

	    @Column(nullable = false)
	    private LocalDateTime expirationDate;

	    public EsqueceuSenhaToken() {
	    }

	    public EsqueceuSenhaToken(String token, Usuario usuario, LocalDateTime expirationDate) {
	        this.token = token;
	        this.usuario = usuario;
	        this.expirationDate = expirationDate;
	    }

	    public Long getId() {
	        return id;
	    }

	    public String getToken() {
	        return token;
	    }

	    public void setToken(String token) {
	        this.token = token;
	    }

	    public Usuario getUsuario() {
	        return usuario;
	    }

	    public void setUsuario(Usuario usuario) {
	        this.usuario = usuario;
	    }

	    public LocalDateTime getExpirationDate() {
	        return expirationDate;
	    }

	    public void setExpirationDate(LocalDateTime expirationDate) {
	        this.expirationDate = expirationDate;
	    }

}
