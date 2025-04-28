package br.com.catedral.visitacao.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.catedral.visitacao.model.Agenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

	@Query(value = "SELECT * FROM agenda a " + "WHERE EXTRACT(MONTH FROM a.data_hora) = :mes "
			+ "AND EXTRACT(YEAR FROM a.data_hora) = EXTRACT(YEAR FROM CURRENT_DATE)", nativeQuery = true)
	List<Agenda> buscarAgendaPorMes(@Param("mes") int mes);

	@Query(value = "SELECT * FROM agenda a " + "WHERE EXTRACT(MONTH FROM a.data_hora) = :mes "
			+ "AND EXTRACT(DAY FROM a.data_hora) = :dia "
			+ "AND EXTRACT(YEAR FROM a.data_hora) = EXTRACT(YEAR FROM CURRENT_DATE)", nativeQuery = true)
	List<Agenda> buscarAgendaPorMesEDia(@Param("mes") int mes, @Param("dia") int dia);
}
