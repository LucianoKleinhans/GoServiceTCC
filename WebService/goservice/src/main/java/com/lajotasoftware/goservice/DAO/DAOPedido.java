package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DAOPedido extends JpaRepository<Pedido, Long> {

    @Query(value = "select * from pedido p where p.id_prestador_id = ?1 and p.servico_solicitado = true and p.status = 'A'", nativeQuery = true)
    List<Pedido> getPedidosPrestador(Long id);

    @Query(value = "select * from pedido p where p.id_cliente_id = ?1 and p.servico_solicitado = true and p.status = 'A'", nativeQuery = true)
    List<Pedido> getPedidosCliente(Long id);
}
