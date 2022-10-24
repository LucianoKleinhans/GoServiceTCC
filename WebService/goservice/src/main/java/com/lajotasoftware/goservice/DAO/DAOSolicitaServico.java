package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Proposta;
import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DAOSolicitaServico extends JpaRepository<SolicitaServico, Long> {

    @Query(value = "select * from solicita_servico ss where ss.id = ?1", nativeQuery = true)
    SolicitaServico getCardServicoByid(Long id);

    @Query(value = "select * from solicita_servico ss where ss.id_cliente_id = ?1 and ss.excluido = false and ss.status = 'ABERTO'", nativeQuery = true)
    List<SolicitaServico> getCardServicos(Long id);

    @Query(value = "select * from solicita_servico ss where ss.id_cliente_id != ?1 and ss.excluido = false and ss.status = 'ABERTO'", nativeQuery = true)
    List<SolicitaServico> getCardsServicoPublico(long id);

    @Query(value = "select * from solicita_servico ss where ss.id_cliente_id = ?1 and ss.status != 'ABERTO'", nativeQuery = true)
    List<SolicitaServico> getCardsServicoFinalizados(Long id);

    @Query(value = "select MIN(p.valor) from proposta p where p.id_solicita_servico_id = ?1", nativeQuery = true)
    Double getMenorValorProposto(Long id);

    @Query(value = "update solicita_servico set valor_proposto = ?2 where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void setMenorvalorProposto(Long id, Double valor_proposto);
}
