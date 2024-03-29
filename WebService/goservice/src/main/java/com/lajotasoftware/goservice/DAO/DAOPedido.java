package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DAOPedido extends JpaRepository<Pedido, Long> {

    @Query(value = "select * from pedido p where p.id_prestador_id = ?1 and p.servico_solicitado = false and p.status = 'ABERTO'", nativeQuery = true)
    List<Pedido> getPedidosPrestador(Long id);

    @Query(value = "select * from pedido p where p.id_cliente_id = ?1 and p.servico_solicitado = false and p.status = 'ABERTO'", nativeQuery = true)
    List<Pedido> getPedidosCliente(Long id);

//    @Query(value = "select * from pedido p where (p.id_cliente_id = ?1 or p.id_prestador_id = ?1) and p.servico_solicitado = false and p.status = 'ACEITO'", nativeQuery = true)
//    List<Pedido> getPedidosEmProgresso(Long id);

    @Query(value = "select * from pedido p where (p.id_cliente_id = ?1 and p.avaliacao_prestador is null and (p.status = 'ACEITO' or p.status = 'FINALIZADO')) or (p.id_prestador_id = ?1 and p.avaliacao_cliente is null and (p.status = 'ACEITO' or p.status = 'FINALIZADO'))", nativeQuery = true)
    List<Pedido> getPedidosEmProgresso(Long id);

    @Query(value = "select * from pedido p where (p.id_cliente_id = ?1 or p.id_prestador_id = ?1) and (p.status = 'RECUSADO' or p.status = 'FINALIZADO' or p.status = 'CANCELADO')", nativeQuery = true)
    List<Pedido> getPedidosFinalizados(Long id);

    @Query(value = "select * from pedido p where p.id = ?1", nativeQuery = true)
    Pedido getPedidoId(Long id);

    @Query(value = "select * from pedido p where (p.id_cliente_id = ?1 and p.id_servico_id = ?2) and p.status = 'ABERTO'", nativeQuery = true)
    List<Pedido> verificaSeExiste(Long idCliente, Long idServico);

    @Query(value = "update pedido set status = ?2 where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void setStatusPedido(Long idPedido, String status);

    /*
    @Query(value = "select * from pedido p where p.id_cliente_id = ?1 and p.servico_solicitado = true and p.status = 'ACEITO'", nativeQuery = true)
    List<Pedido> getPedidosEmProgressoCliente(Long id);

    @Query(value = "select * from pedido p where p.id_prestador_id = ?1 and p.servico_solicitado = true and p.status = 'ACEITO'", nativeQuery = true)
    List<Pedido> getPedidosEmProgressoPrestador(Long id);

    @Query(value = "select * from pedido p where p.id_cliente_id = ?1 and p.servico_solicitado = true and p.status = 'RECUSADO' or p.status = 'FINALIZADO' or p.status = 'CANCELADO'", nativeQuery = true)
    List<Pedido> getPedidosFinalizadosCliente(Long id);

    @Query(value = "select * from pedido p where p.id_prestador_id = ?1 and p.servico_solicitado = true and p.status = 'RECUSADO' or p.status = 'FINALIZADO' or p.status = 'CANCELADO'", nativeQuery = true)
    List<Pedido> getPedidosFinalizadosPrestador(Long id);
     */
}
