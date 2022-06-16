package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOProposta;
import com.lajotasoftware.goservice.Entity.Proposta;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@EnableJpaRepositories(basePackages = "com.lajotasoftware.goservice.DAO")
@RestController
@AllArgsConstructor
public class PropostaController {

    DAOProposta repository;

    @GetMapping("/proposta")
    public List<Proposta> getAllProposta(){
        return repository.findAll();
    }

    @GetMapping("/proposta/{id}")
    public Proposta getPropostaById(@PathVariable Long id){
        return repository.getById(id);
    }

    @PostMapping("/proposta")
    public Proposta salvarProposta(@RequestBody Proposta proposta){
        return repository.save(proposta);
    }

    @DeleteMapping("/proposta/{id}")
    public void deleteProposta(@PathVariable Long id){
        repository.deleteById(id);
    }


}