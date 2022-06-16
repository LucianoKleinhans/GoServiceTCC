package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DAOProposta extends JpaRepository<Proposta, Long> {

}
