package br.com.catedral.visitacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.catedral.visitacao.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{

}
