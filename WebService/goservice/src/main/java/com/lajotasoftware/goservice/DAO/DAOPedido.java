package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DAOPedido extends JpaRepository<Pedido, Long> {

}
