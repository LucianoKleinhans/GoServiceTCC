package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DAOSolicitaServico extends JpaRepository<SolicitaServico, Long> {

    @Query(value = "select * from solicita_servico ss where ss.id = ?1", nativeQuery = true)
    SolicitaServico getCardServicoByid(Long id);

    @Query(value = "select * from solicita_servico ss where ss.id_cliente_id = ?1 and ss.excluido = false", nativeQuery = true)
    List<SolicitaServico> getCardServicos(Long id);

    @Query(value = "select * from solicita_servico ss where ss.id_cliente_id != ?1 and ss.excluido = false", nativeQuery = true)
    List<SolicitaServico> getCardsServicoPublico(long id);
}
