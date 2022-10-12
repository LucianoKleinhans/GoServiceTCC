package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DAOUsuario extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM Usuario u WHERE u.id = ?1", nativeQuery = true)
    Usuario findPrestadorById(Long id);

    @Query(value = "select * from usuario u where u.ativo = true and u.prestador = true and u.id != ?1", nativeQuery = true)
    List<Usuario> findAllPrestadores(Long id);

    Usuario findByLogin(String login);

    @Query(value = "select * from usuario u where u.id = ?1", nativeQuery = true)
    Usuario getUsuario(Long id);

    @Query(value = "update usuario set avatarimg = ?2 where id = ?1", nativeQuery = true)
    default boolean saveImageUrl(Long user, String fileName){

        return true;
    };
}
