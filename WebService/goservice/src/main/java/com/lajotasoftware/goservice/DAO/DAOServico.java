package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DAOServico extends JpaRepository<Servico, Long> {

    @Query(value = "select * from servico s where s.id_prestador_id = ?1", nativeQuery = true)
    List<Servico> getServicosPrestador(Long idPrestador);
}
