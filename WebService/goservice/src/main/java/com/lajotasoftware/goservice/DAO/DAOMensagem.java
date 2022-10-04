package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DAOMensagem extends JpaRepository<Mensagem, Long> {

    @Query(value = "select * from mensagem m where m.id_proposta_id = ?1", nativeQuery = true)
    List<Mensagem> getMensagemProposta(Long id);
}
