package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOServico;
import com.lajotasoftware.goservice.Entity.Servico;
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
public class ServicoController {

    DAOServico repository;

    @Autowired
    UserService userService;
    @GetMapping("/servico")
    public List<Servico> getAllServico(){
        return repository.findAll();
    }

    @GetMapping("/servico/{id}")
    public Servico getServicoById(@PathVariable Long id){
        return repository.getById(id);
    }

    @PostMapping("/servico/create")
    public Servico salvarServico(@RequestBody Servico servico){ return repository.save(servico);
    }

    /*@PostMapping("/servico/create")
    public Servico salvarServico(@RequestBody Servico servico){
        return repository.savar(servico.getNome(), servico.getObsServico(), servico.getValor(), servico.getId_Prestador().getId(), servico.getId_Categoria().getId(), servico.getId_SubCategoria().getId());
    }*/


        @DeleteMapping("/servico/delete/{id}")
    public void deleteServico(@PathVariable Long id){
        repository.deleteById(id);
    }

    @PutMapping(value = "/servico/update/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Servico servico) {
        return repository.findById(id)
                .map(record -> {
                    if(servico.getId_Prestador()!=null){record.setId_Prestador(servico.getId_Prestador());}
                    if(servico.getId()!=null){record.setId(servico.getId());}
                    if(servico.getNome()!=null){record.setNome(servico.getNome());}
                    if(servico.getId_Categoria()!=null){record.setId_Categoria(servico.getId_Categoria());}
                    if(servico.getId_SubCategoria()!=null){record.setId_SubCategoria(servico.getId_SubCategoria());}
                    if(servico.getValor()!=null){record.setValor(servico.getValor());}
                    if(servico.getExcluido()!=null){record.setExcluido(servico.getExcluido());}
                    if(servico.getObsServico()!=null){record.setObsServico(servico.getObsServico());}
                    Servico updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/usuario/servico/{idPrestador}")
    public List<Servico> getServicosPrestador(@PathVariable Long idPrestador){
        return repository.getServicosPrestador(idPrestador);
    }

    @PostMapping("/servico/seleciona/{id}")
    public Servico getServicoByID(@PathVariable Long id){
        return repository.getServicoByID(id);
    }

    @PostMapping("/servico/lista/{id}")
    public List<Servico> getAllServicos(@PathVariable Long id){
        return repository.getAllServicos(id);
    }

    @PostMapping("/servico/lista/categoria/{idCategoria}/{idSubCategoria}")
    public List<Servico> getAllServicosByCategoria(@PathVariable Long idCategoria,
                                                   @PathVariable Long idSubCategoria) {
        return userService.getServicoByCategoria(idCategoria, idSubCategoria);
    }
}