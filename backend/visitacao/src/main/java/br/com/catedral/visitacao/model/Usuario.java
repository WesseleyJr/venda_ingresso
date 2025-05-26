package br.com.catedral.visitacao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(length = 100, name = "primeiro_nome")
	@Size(max = 100)
	@NotBlank(message = "Nome não pode ser nulo")
	private String nome;

	@Column(length = 100)
	@Size(max = 100)
	@Email
	@NotBlank(message = "Email não pode ser nulo")
	private String email;

	@NotBlank(message = "Senha não pode ser nulo")
	private String senha;

	@Column(length = 11)
	@Size(max = 11)
	@CPF
	private String cpf;

	@OneToMany(mappedBy = "id.usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<UsuarioPerfil> usuarioPerfis = new HashSet<>();

	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Ingresso> ingressos = new HashSet<>();

	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Guia> guias = new HashSet<>();

	public Set<Guia> getGuias() {
		return guias;
	}

	public void setGuias(Set<Guia> guias) {
		this.guias = guias;
	}

	public Set<Ingresso> getIngressos() {
		return ingressos;
	}

	public void setIngressos(Set<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}

	public Usuario() {
	}

	public Usuario(Long id, String nome, String email, String senha, String cpf, Set<UsuarioPerfil> usuarioPerfis,
			Set<Ingresso> ingressos, Set<Guia> guias) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.usuarioPerfis = usuarioPerfis;
		this.ingressos = ingressos;
		this.guias = guias;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<UsuarioPerfil> getUsuarioPerfis() {
		return usuarioPerfis;
	}

	public void setUsuarioPerfis(Set<UsuarioPerfil> usuarioPerfis) {
		this.usuarioPerfis = usuarioPerfis;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (UsuarioPerfil usuarioPerfil : getUsuarioPerfis()) {
			authorities.add(new SimpleGrantedAuthority(usuarioPerfil.getId().getPerfil().getNome()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String toString() {
		return "* Código: " + id + "\n* Nome: " + nome + "\n* Email: " + email + "\n* Perfis: "
				+ usuarioPerfis.stream().map(up -> up.getId().getPerfil().getNome()).collect(Collectors.joining(", "));
	};
}