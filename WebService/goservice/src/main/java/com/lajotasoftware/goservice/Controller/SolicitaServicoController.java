package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOSolicitaServico;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Entity.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
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

    @PutMapping(value = "/solicitaServico/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody SolicitaServico solicitaServico) {
        return repository.findById(id)
                .map(record -> {
                    if(solicitaServico.getId_Cliente()!=null){record.setId_Cliente(solicitaServico.getId_Cliente());}
                    if(solicitaServico.getId()!=null){record.setId(solicitaServico.getId());}
                    if(solicitaServico.getValor()!=null){record.setValor(solicitaServico.getValor());}
                    if(solicitaServico.getCategoria()!=null){record.setCategoria(solicitaServico.getCategoria());}
                    if(solicitaServico.getDescricaoSolicitacao()!=null){record.setDescricaoSolicitacao(solicitaServico.getDescricaoSolicitacao());}
                    SolicitaServico updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
}