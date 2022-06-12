package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DAOMensagem extends JpaRepository<Mensagem, Long> {

}
