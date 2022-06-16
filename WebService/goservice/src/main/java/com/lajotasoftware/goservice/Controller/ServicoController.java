package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOServico;
import com.lajotasoftware.goservice.Entity.Servico;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@EnableJpaRepositories(basePackages = "com.lajotasoftware.goservice.DAO")
@RestController
@AllArgsConstructor
public class ServicoController {

    DAOServico repository;

    @GetMapping("/servico")
    public List<Servico> getAllServico(){
        return repository.findAll();
    }

    @GetMapping("/servico/{id}")
    public Servico getServicoById(@PathVariable Long id){
        return repository.getById(id);
    }

    @PostMapping("/servico")
    public Servico salvarUsuario(@RequestBody Servico servico){ return repository.save(servico);
    }

    @DeleteMapping("/servico/{id}")
    public void deleteServico(@PathVariable Long id){
        repository.deleteById(id);
    }


}