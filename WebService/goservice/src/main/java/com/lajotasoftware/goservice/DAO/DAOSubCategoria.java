package com.lajotasoftware.goservice.DAO;

import com.lajotasoftware.goservice.Entity.SubCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DAOSubCategoria extends JpaRepository<SubCategoria, Long> {

    @Query(value = "select * from sub_categoria scs where scs.categoria_servico_id = ?1", nativeQuery = true)
    List<SubCategoria> findSubCategoriaByCategoria(Long id);

    @Query(value = "select * from sub_categoria", nativeQuery = true)
    List<SubCategoria> findAllSubCategoria();
}
