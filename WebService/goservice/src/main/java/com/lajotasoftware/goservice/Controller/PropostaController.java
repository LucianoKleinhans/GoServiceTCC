package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOProposta;
import com.lajotasoftware.goservice.Entity.Proposta;
import com.lajotasoftware.goservice.Entity.Return;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@EnableJpaRepositories(basePackages = "com.lajotasoftware.goservice.DAO")
@RestController
@AllArgsConstructor
public class PropostaController {

    DAOProposta repository;

    @Autowired
    UserService userService;

    @GetMapping("/proposta")
    public List<Proposta> getAllProposta(){
        return repository.findAll();
    }

    @PostMapping("/proposta/{id}")
    public Proposta getPropostaById(@PathVariable Long id){
        return repository.getPropostaById(id);
    }

    @PostMapping("/proposta/create")
    public Proposta salvarProposta(@RequestBody Proposta proposta){
        proposta = repository.save(proposta);
        userService.notificaCliente(proposta);
        userService.setValorProposto(proposta.getId_SolicitaServico().getId(), proposta.getValor());
        return proposta;
    }

    @DeleteMapping("/proposta/{id}")
    public void deleteProposta(@PathVariable Long id){
        repository.deleteById(id);
    }

    @PutMapping(value = "/proposta/update/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Proposta proposta) {
        if (proposta.getStatus().equals("ABERTO")){
            userService.setValorProposto(proposta.getId_SolicitaServico().getId(), proposta.getValor());
        }
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

    @PostMapping(value = "/proposta/card/{idSolicitaServico}/{idPrestador}")
    public Proposta getPropostaSolicitacaoServico(@PathVariable("idSolicitaServico") Long idSolicitaServico,
                                                  @PathVariable("idPrestador") Long idPrestador){
        return repository.getPropostaSolicitacaoServico(idSolicitaServico,idPrestador);
    }

    @PostMapping(value = "/proposta/card/propostajafeita/{idprestador}/{idsolicitacao}")
    public Return getPropostaJaFeita(@PathVariable("idprestador") Long idPrestador,
                                     @PathVariable("idsolicitacao") Long idSolicitacao){
        return userService.getPropostaFeita(idPrestador, idSolicitacao);
    }

    @PostMapping(value = "/proposta/card/statusProposta/{idproposta}/{status}")
    public Return setStatusProposta(@PathVariable("idproposta") Long idProposta,
                                    @PathVariable("status") String status){
        return userService.setStatusProposta(idProposta, status);
    }
}