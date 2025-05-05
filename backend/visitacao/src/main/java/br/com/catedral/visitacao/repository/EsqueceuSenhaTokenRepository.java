package br.com.catedral.visitacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.catedral.visitacao.model.EsqueceuSenhaToken;
import br.com.catedral.visitacao.model.Usuario;

public interface EsqueceuSenhaTokenRepository extends JpaRepository< EsqueceuSenhaToken , Long>{
	
	Optional<EsqueceuSenhaToken> findByUsuario(Usuario usuario);
	
	EsqueceuSenhaToken findByToken(String token);

}
