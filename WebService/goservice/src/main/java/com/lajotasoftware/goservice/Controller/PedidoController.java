package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOPedido;
import com.lajotasoftware.goservice.Entity.Pedido;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableJpaRepositories(basePackages = "com.lajotasoftware.goservice.DAO")
@RestController
@AllArgsConstructor
public class PedidoController {

    DAOPedido repository;

    @GetMapping("/pedido")
    public List<Pedido> getAllPedidos(){
        return repository.findAll();
    }

    @GetMapping("/pedido/{id}")
    public Pedido getPedidoById(@PathVariable Long id){
        return repository.getById(id);
    }

    @PostMapping("/pedido")
    public Pedido salvarPedido(@RequestBody Pedido pedido){
        return repository.save(pedido);
    }

    @DeleteMapping("/pedido/{id}")
    public void deletePedido(@PathVariable Long id){
        repository.deleteById(id);
    }


}
