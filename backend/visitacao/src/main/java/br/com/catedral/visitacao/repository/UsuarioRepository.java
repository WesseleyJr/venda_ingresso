package br.com.catedral.visitacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.catedral.visitacao.model.Usuario;



@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByEmail(String email);
	
	Usuario findByCpf(String email);
	
}
