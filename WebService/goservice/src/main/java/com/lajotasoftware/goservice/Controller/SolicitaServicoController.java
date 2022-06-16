package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOSolicitaServico;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@EnableJpaRepositories(basePackages = "com.lajotasoftware.goservice.DAO")
@RestController
@AllArgsConstructor
public class SolicitaServicoController {

    DAOSolicitaServico repository;

    @GetMapping("/solicitaServico")
    public List<SolicitaServico> getAllSolicitaServico(){ return repository.findAll();
    }

    @GetMapping("/solicitaServico/{id}")
    public SolicitaServico getSolicitaServicoById(@PathVariable Long id){ return repository.getById(id);
    }

    @PostMapping("/solicitaServico")
    public SolicitaServico salvarSolicitaServico(@RequestBody SolicitaServico solicitaServico){ return repository.save(solicitaServico);
    }

    @DeleteMapping("/solicitaServico/{id}")
    public void deleteSolicitaServico (@PathVariable Long id){
        repository.deleteById(id);
    }


}