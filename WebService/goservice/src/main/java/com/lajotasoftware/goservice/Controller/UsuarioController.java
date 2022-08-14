package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOUsuario;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.Services.CreateUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.*;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories(basePackages = "com.lajotasoftware.goservice.DAO")
@RestController
@AllArgsConstructor
public class UsuarioController {

    DAOUsuario repository;

    @Autowired
    CreateUserService createUserService;

    @GetMapping("/usuario")
    public List<Usuario> getAllUsuarios() {
        return repository.findAll();
    }

    @GetMapping("/usuario/{id}")
    public Usuario getUsuarioById(@PathVariable Long id) {
        return repository.findById(id).get();
    }

    //autenticação do login do usuario

    @GetMapping("/usuarioPrestador")
    public List<Usuario> getAllUsuariosPrestador() {
        return repository.findAllPrestadores();
    }

    @GetMapping("/usuarioPrestador/{id}")
    public List<Usuario> getPrestador(@PathVariable Long id) {
        return repository.findPrestadorById(id);
    }

    /*@PostMapping("/usuario/create")
    public Usuario salvarUsuario(@RequestBody Usuario usuario) {
        return repository.save(usuario);
    }*/
    @PostMapping("/usuario/create")
    public Usuario createUser(@RequestBody Usuario usuario) {
        return createUserService.execute(usuario);
    }
    @PostMapping("/usuario/validation")
    public Usuario validateUser(@RequestBody Usuario usuario) {
        return createUserService.validation(usuario);
    }

    @DeleteMapping("/usuario/delete/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping(value = "/usuario/update/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Usuario usuario) {
        return repository.findById(id)
            .map(record -> {
                if(usuario.getId_Prestador()!=null){record.setId_Prestador(usuario.getId_Prestador());}
                if(usuario.getPrimeiroNome()!=null){record.setPrimeiroNome(usuario.getPrimeiroNome());}
                if(usuario.getSegundoNome()!=null){record.setSegundoNome(usuario.getSegundoNome());}
                if(usuario.getCpf()!=null){record.setCpf(usuario.getCpf());}
                if(usuario.getCnpj()!=null){record.setCnpj(usuario.getCnpj());}
                if(usuario.getRuaAvenida()!=null){record.setRuaAvenida(usuario.getRuaAvenida());}
                if(usuario.getBairro()!=null){record.setBairro(usuario.getBairro());}
                if(usuario.getNumero()!=null){record.setNumero(usuario.getNumero());}
                if(usuario.getCidade()!=null){record.setCidade(usuario.getCidade());}
                if(usuario.getCep()!=null){record.setCep(usuario.getCep());}
                if(usuario.getUf()!=null){record.setUf(usuario.getUf());}
                if(usuario.getTelefone()!=null){record.setTelefone(usuario.getTelefone());}
                if(usuario.getEmail()!=null){record.setEmail(usuario.getEmail());}
                if(usuario.getSite()!=null){record.setSite(usuario.getSite());}
                if(usuario.getLogin()!=null){record.setLogin(usuario.getLogin());}
                if(usuario.getSenha()!=null){record.setSenha(usuario.getSenha());}
                if(usuario.getAvaliacaoPrestador()!=null){record.setAvaliacaoPrestador(usuario.getAvaliacaoPrestador());}
                if(usuario.getAvaliacaoCliente()!=null){record.setAvaliacaoCliente(usuario.getAvaliacaoCliente());}
                if(usuario.getPrestador()!=null){record.setPrestador(usuario.getPrestador());}
                if(usuario.getAtivo()!=null){record.setAtivo(usuario.getAtivo());}
                Usuario updated = repository.save(record);
                return ResponseEntity.ok().body(updated);
            }).orElse(ResponseEntity.notFound().build());
    }
}
