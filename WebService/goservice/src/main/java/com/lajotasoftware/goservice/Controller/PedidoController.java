package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOPedido;
import com.lajotasoftware.goservice.Entity.Pedido;
import com.lajotasoftware.goservice.Entity.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
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

    @PutMapping(value = "/pedido/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Pedido pedido) {
        return repository.findById(id)
                .map(record -> {
                    if(pedido.getId_Cliente()!=null){record.setId_Cliente(pedido.getId_Cliente());}
                    if(pedido.getId_Prestador()!=null){record.setId_Prestador(pedido.getId_Prestador());}
                    if(pedido.getId_ServicoSolicitado()!=null){record.setId_ServicoSolicitado(pedido.getId_ServicoSolicitado());}
                    if(pedido.getId_Servico()!=null){record.setId_Servico(pedido.getId_Servico());}
                    if(pedido.getId()!=null){record.setId(pedido.getId());}
                    if(pedido.getStatus()!=null){record.setStatus(pedido.getStatus());}
                    if(pedido.getAvaliacaoCliente()!=null){record.setAvaliacaoCliente(pedido.getAvaliacaoCliente());}
                    if(pedido.getAvaliacaoPrestador()!=null){record.setAvaliacaoPrestador(pedido.getAvaliacaoPrestador());}
                    if(pedido.getServicoSolicitado()!=null){record.setServicoSolicitado(pedido.getServicoSolicitado());}
                    if(pedido.getDataEmissao()!=null){record.setDataEmissao(pedido.getDataEmissao());}
                    if(pedido.getDataFinalizacao()!=null){record.setDataFinalizacao(pedido.getDataFinalizacao());}
                    Pedido updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
}
