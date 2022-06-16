package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DAOServico extends JpaRepository<Servico, Long> {

}
