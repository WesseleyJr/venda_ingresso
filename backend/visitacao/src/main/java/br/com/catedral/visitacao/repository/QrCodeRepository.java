package br.com.catedral.visitacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.catedral.visitacao.model.QrCode;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, Long> {
}
