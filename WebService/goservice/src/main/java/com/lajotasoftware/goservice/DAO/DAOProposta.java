package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DAOProposta extends JpaRepository<Proposta, Long> {

    @Query(value = "select * from proposta p where p.id_prestador_id = ?1 and p.status = 'ABERTA'", nativeQuery = true)
    List<Proposta> getPropostasEnviadas(Long id);

    @Query(value = "select * from proposta p where p.id_solicita_servico_id  = ?1 and p.status = 'ABERTA' ", nativeQuery = true)
    List<Proposta> getPropostasRecebidas(Long id);
}
