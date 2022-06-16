package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOUsuario;
import com.lajotasoftware.goservice.Entity.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@EnableJpaRepositories(basePackages = "com.lajotasoftware.goservice.DAO")
@RestController
@AllArgsConstructor
public class UsuarioController {

    DAOUsuario repository;

    @GetMapping("/usuario")
    public List<Usuario> getAllUsuarios(){
        return repository.findAll();
    }

    @GetMapping("/usuario/{id}")
    public Usuario getUsuarioById(@PathVariable Long id){
        return repository.getById(id);
    }

    @PostMapping("/usuario")
    public Usuario salvarUsuario(@RequestBody Usuario usuario){
        return repository.save(usuario);
    }

    @DeleteMapping("/usuario/{id}")
    public void deleteUsuario(@PathVariable Long id){
        repository.deleteById(id);
    }


}
