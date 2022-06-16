package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOMensagem;
import com.lajotasoftware.goservice.Entity.Mensagem;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
    public void deleteMensagem(@PathVariable Long id){ repository.deleteById(id);
    }


}