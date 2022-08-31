package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DAOSolicitaServico extends JpaRepository<SolicitaServico, Long> {

    @Query(value = "select * from solicita_servico ss where ss.nome_servico like ?1 and ss.descricao_solicitacao  like ?2 and ss.valor = ?3", nativeQuery = true)
    SolicitaServico getCardServicoByNDV(String nomeServ, String descServ, Double valorServ);

    @Query(value = "select * from solicita_servico ss where ss.id_cliente_id = ?1", nativeQuery = true)
    List<SolicitaServico> getCardServicos(Long id);

}
