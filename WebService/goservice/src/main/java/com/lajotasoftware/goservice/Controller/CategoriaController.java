package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOCategoria;
import com.lajotasoftware.goservice.DAO.DAOSubCategoria;
import com.lajotasoftware.goservice.Entity.Categoria;
import com.lajotasoftware.goservice.Entity.SubCategoria;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableJpaRepositories(basePackages = "com.lajotasoftware.goservice.DAO")
@RestController
@AllArgsConstructor
public class CategoriaController {

    DAOCategoria repositoryCategoria;
    DAOSubCategoria repositorySubCategoria;

    @PostMapping("/categoria")
    public List<Categoria> getAllCategoria(){
        return repositoryCategoria.findAllCategoria();
    }

    @PostMapping("/subcategoria")
    public List<SubCategoria> getAllSubCategoria(){
        return repositorySubCategoria.findAllSubCategoria();
    }

    @PostMapping("/subcategoria/{id}")
    public List<SubCategoria> getSubCategoriaByCategoria(@PathVariable Long id){
        return repositorySubCategoria.findSubCategoriaByCategoria(id);
    }
}
