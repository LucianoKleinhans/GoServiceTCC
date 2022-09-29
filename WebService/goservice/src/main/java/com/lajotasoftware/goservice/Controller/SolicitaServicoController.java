package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOSolicitaServico;
import com.lajotasoftware.goservice.Entity.Servico;
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

    @GetMapping("/cardservico")
    public List<SolicitaServico> getAllSolicitaServico(){ return repository.findAll();
    }

    @GetMapping("/cardservico/{id}")
    public SolicitaServico getSolicitaServicoById(@PathVariable Long id){ return repository.getById(id);
    }

    @PostMapping("/cardservico/create")
    public SolicitaServico salvarSolicitaServico(@RequestBody SolicitaServico solicitaServico){ return repository.save(solicitaServico);
    }

    @DeleteMapping("/cardservico/delete/{id}")
    public void deleteSolicitaServico (@PathVariable Long id){
        repository.deleteById(id);
    }

    @PutMapping(value = "/cardservico/update/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody SolicitaServico solicitaServico) {
        return repository.findById(id)
                .map(record -> {
                    if(solicitaServico.getId_Cliente()!=null){record.setId_Cliente(solicitaServico.getId_Cliente());}
                    if(solicitaServico.getId()!=null){record.setId(solicitaServico.getId());}
                    if(solicitaServico.getNomeServico()!=null){record.setNomeServico(solicitaServico.getNomeServico());}
                    if(solicitaServico.getValor()!=null){record.setValor(solicitaServico.getValor());}
                    if(solicitaServico.getValorProposto()!=null){record.setValorProposto(solicitaServico.getValorProposto());}
                    if(solicitaServico.getId_Categoria()!=null){record.setId_Categoria(solicitaServico.getId_Categoria());}
                    if(solicitaServico.getId_SubCategoria()!=null){record.setId_SubCategoria(solicitaServico.getId_SubCategoria());}
                    if(solicitaServico.getDescricaoSolicitacao()!=null){record.setDescricaoSolicitacao(solicitaServico.getDescricaoSolicitacao());}
                    SolicitaServico updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cardservico/{id}")
    public List<SolicitaServico> getCardServicos(@PathVariable Long id){
        return repository.getCardServicos(id);
    }

    @PostMapping("/cardservico/seleciona/{id}")
    public SolicitaServico getCardServicoByid(@PathVariable Long id){
        return repository.getCardServicoByid(id);
    }
}