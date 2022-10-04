package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOMensagem;
import com.lajotasoftware.goservice.Entity.Mensagem;
import com.lajotasoftware.goservice.Entity.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@EnableJpaRepositories(basePackages = "com.lajotasoftware.goservice.DAO")
@RestController
@AllArgsConstructor
public class MensagemController {

    DAOMensagem repository;

    @GetMapping("/mensagem")
    public List<Mensagem> getAllMensagem(){
        return repository.findAll();
    }

    @GetMapping("/mensagem/{id}")
    public Mensagem getMensagemById(@PathVariable Long id){
        return repository.getById(id);
    }

    @PostMapping("/mensagem")
    public Mensagem salvarMensagem(@RequestBody Mensagem mensagem){
        return repository.save(mensagem);
    }

    @DeleteMapping("/mensagem/{id}")
    public void deleteMensagem(@PathVariable Long id){ repository.deleteById(id);}

    @PutMapping(value = "/mensagem/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Mensagem mensagem) {
        return repository.findById(id)
                .map(record -> {
                    if(mensagem.getId_Proposta()!=null){record.setId_Proposta(mensagem.getId_Proposta());}
                    if(mensagem.getId_Prestador()!=null){record.setId_Prestador(mensagem.getId_Prestador());}
                    if(mensagem.getId_Cliente()!=null){record.setId_Cliente(mensagem.getId_Cliente());}
                    if(mensagem.getId()!=null){record.setId(mensagem.getId());}
                    if(mensagem.getDataHoraMsg()!=null){record.setDataHoraMsg(mensagem.getDataHoraMsg());}
                    if(mensagem.getMensagem()!=null){record.setMensagem(mensagem.getMensagem());}
                    Mensagem updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/mensagem/proposta/{id}")
    public Mensagem getMensagemProposta(@PathVariable Long id){
        return repository.getMensagemProposta(id);
    }
}