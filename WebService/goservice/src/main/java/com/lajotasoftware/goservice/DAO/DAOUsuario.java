package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DAOUsuario extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM Usuario u WHERE u.id_Prestador = ?1 and u.ativo = true", nativeQuery = true)
    List<Usuario> findPrestadorById(Long id);

    @Query(value = "SELECT * FROM Usuario u WHERE u.ativo = true", nativeQuery = true)
    List<Usuario> findAllPrestadores();
}
