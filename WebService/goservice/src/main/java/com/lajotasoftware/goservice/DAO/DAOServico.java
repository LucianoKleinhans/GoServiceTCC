package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.testng.annotations.Optional;

import java.util.List;

@Repository
public interface DAOServico extends JpaRepository<Servico, Long> {

    @Query(value = "select * from servico s where s.id_prestador_id = ?1", nativeQuery = true)
    List<Servico> getServicosPrestador(Long idPrestador);

    @Query(value = "select * from servico s where s.id = ?1", nativeQuery = true)
    Servico getServicoByID(Long id);

    @Query(value = "select * from servico s where id_prestador_id != ?1", nativeQuery = true)
    List<Servico> getAllServicos(Long id);
}
