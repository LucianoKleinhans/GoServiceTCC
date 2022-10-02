package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOProposta;
import com.lajotasoftware.goservice.Entity.Proposta;
import com.lajotasoftware.goservice.Entity.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/proposta/create")
    public Proposta salvarProposta(@RequestBody Proposta proposta){
        return repository.save(proposta);
    }

    @DeleteMapping("/proposta/{id}")
    public void deleteProposta(@PathVariable Long id){
        repository.deleteById(id);
    }

    @PutMapping(value = "/proposta/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Proposta proposta) {
        return repository.findById(id)
                .map(record -> {
                    if(proposta.getId_Prestador()!=null){record.setId_Prestador(proposta.getId_Prestador());}
                    if(proposta.getId_SolicitaServico()!=null){record.setId_SolicitaServico(proposta.getId_SolicitaServico());}
                    if(proposta.getId_Cliente()!=null){record.setId_Cliente(proposta.getId_Cliente());}
                    if(proposta.getId()!=null){record.setId(proposta.getId());}
                    if(proposta.getValor()!=null){record.setValor(proposta.getValor());}
                    if(proposta.getObservacao()!=null){record.setObservacao(proposta.getObservacao());}
                    if(proposta.getStatus()!=null){record.setStatus(proposta.getStatus());}
                    Proposta updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/proposta/enviadas/{id}")
    public List<Proposta> getPropostasEnviadas(@PathVariable("id") Long id){
        return repository.getPropostasEnviadas(id);
    }
    @PostMapping(value = "/proposta/recebidas/{id}")
    public List<Proposta> getPropostasRecebidas(@PathVariable("id") Long id){
        return repository.getPropostasRecebidas(id);
    }
}