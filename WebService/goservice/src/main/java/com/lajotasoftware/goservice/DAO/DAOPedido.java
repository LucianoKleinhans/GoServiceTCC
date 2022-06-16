package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DAOPedido extends JpaRepository<Pedido, Long> {

}
