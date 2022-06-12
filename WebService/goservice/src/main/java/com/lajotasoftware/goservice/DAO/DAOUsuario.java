package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DAOUsuario extends JpaRepository<Usuario, Long> {

}
