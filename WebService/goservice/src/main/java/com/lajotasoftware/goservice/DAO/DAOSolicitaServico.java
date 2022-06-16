package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.SolicitaServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DAOSolicitaServico extends JpaRepository<SolicitaServico, Long> {

}
