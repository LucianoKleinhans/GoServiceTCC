package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Pedido;
import com.lajotasoftware.goservice.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DAOUsuario extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM Usuario u WHERE u.id = ?1", nativeQuery = true)
    Usuario findPrestadorById(Long id);

    @Query(value = "select * from usuario u where u.ativo = true and u.prestador = true and u.id != ?1", nativeQuery = true)
    List<Usuario> findAllPrestadores(Long id);

    Usuario findByLogin(String login);
    Usuario findByEmail(String email);

    @Query(value = "select * from usuario u where u.id = ?1", nativeQuery = true)
    Usuario getUsuario(Long id);

    @Query(value = "update usuario set avatarimg = ?2 where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void saveImageUrl(Long user, String fileName);

    @Query(value = "update usuario set senha = ?2 where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void alteraSenha(Long id, String novaSenha);

    //-------------------- user calc avaliacao--------------------//

    //contPedidosFinalizados--------------------------------------//
            @Query(value = "select count(*) from pedido p where p.id_prestador_id = ?1 and p.status = 'FINALIZADO'", nativeQuery = true)
            Integer contPedidosFinalizadosPrestador(Long idUser);
            @Query(value = "select count(*) from pedido p where p.id_cliente_id = ?1 and p.status = 'FINALIZADO'", nativeQuery = true)
            Integer contPedidosFinalizadosCliente(Long idUser);

    //contAvaliacao-----------------------------------------------//
            @Query(value = "select sum(p.avaliacao_prestador/?2) from pedido p where p.id_prestador_id = ?1 and p.status = 'FINALIZADO'", nativeQuery = true)
            Double contAvaliacaoPrestador(Long idUser, Integer qtdPedidos);
            @Query(value = "select sum(p.avaliacao_cliente/?2) from pedido p where p.id_cliente_id = ?1 and p.status = 'FINALIZADO'", nativeQuery = true)
            Double contAvaliacaoCliente(Long idUser, Integer qtdPedidos);

    //updateAvaliacao-----------------------------------------------//
            @Query(value = "update usuario set avaliacao_prestador = ?2 where id = ?1", nativeQuery = true)
            @Modifying
            @Transactional
            void updateAvaliacaoPrestador(Long id, Double avaliacao);
            @Query(value = "update usuario set avaliacao_cliente = ?2 where id = ?1", nativeQuery = true)
            @Modifying
            @Transactional
            void updateAvaliacaoCliente(Long id, Double avaliacao);
}
