package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DAOUsuario extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM Usuario u WHERE u.id_Prestador = ?1 and u.ativo = true", nativeQuery = true)
    List<Usuario> findPrestadorById(Long id);

    @Query(value = "SELECT * FROM Usuario u WHERE u.ativo = true", nativeQuery = true)
    List<Usuario> findAllPrestadores();

    Usuario findByLogin(String login);

    //@Query(value = "SELECT u.senha FROM Usuario u WHERE u.login = ?1 AND u.senha = ?2", nativeQuery = true)
    //List<Usuario> VerificaLogin(String login, String senha);

    @Query(value = "SELECT u.id FROM Usuario u WHERE u.login = ?1", nativeQuery = true)
    Usuario findIdByUsuario(Usuario usuario);
}
