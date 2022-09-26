package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DAOCategoria extends JpaRepository<Categoria, Long> {

    @Query(value = "select * from categoria", nativeQuery = true)
    List<Categoria> findAllCategoria();
}