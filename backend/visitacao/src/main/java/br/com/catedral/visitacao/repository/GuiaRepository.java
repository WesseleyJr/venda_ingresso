package br.com.catedral.visitacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.catedral.visitacao.model.Guia;

@Repository
public interface GuiaRepository extends JpaRepository<Guia, Long> {
}
