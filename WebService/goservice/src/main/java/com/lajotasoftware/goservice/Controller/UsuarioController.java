package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOUsuario;
import com.lajotasoftware.goservice.Entity.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableJpaRepositories(basePackages = "com.lajotasoftware.goservice.DAO")
@RestController
@AllArgsConstructor
public class UsuarioController {

    DAOUsuario repository;

    @GetMapping("/usuario")
    public List<Usuario> getAllUsuarios() {
        return repository.findAll();
    }

    @GetMapping("/usuario/{id}")
    public Usuario getUsuarioById(@PathVariable Long id) {
        return repository.findById(id).get();
    }

    @GetMapping("/usuarioPrestador")
    public List<Usuario> getAllUsuariosPrestador() {
        return repository.findAllPrestadores();
    }

    @GetMapping("/usuarioPrestador/{id}")
    public List<Usuario> getPrestador(@PathVariable Long id) {
        return repository.findPrestadorById(id);
    }

    @PostMapping("/usuario")
    public Usuario salvarUsuario(@RequestBody Usuario usuario) {
        return repository.save(usuario);
    }

    @DeleteMapping("/usuario/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping(value = "/usuario/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Usuario usuario) {
        return repository.findById(id)
            .map(record -> {
                if(usuario.getId_Prestador()!=null){record.setId_Prestador(usuario.getId_Prestador());}
                if(usuario.getNome()!=null){record.setNome(usuario.getNome());}
                if(usuario.getCpf()!=null){record.setCpf(usuario.getCpf());}
                if(usuario.getCnpj()!=null){record.setCnpj(usuario.getCnpj());}
                if(usuario.getEndereco()!=null){record.setEndereco(usuario.getEndereco());}
                if(usuario.getTelefone()!=null){record.setTelefone(usuario.getTelefone());}
                if(usuario.getEmail()!=null){record.setEmail(usuario.getEmail());}
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
