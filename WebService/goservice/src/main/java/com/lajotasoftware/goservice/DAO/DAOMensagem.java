package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DAOMensagem extends JpaRepository<Mensagem, Long> {

}
