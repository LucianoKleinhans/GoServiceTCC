package com.lajotasoftware.goservice.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Proposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario id_Cliente;
    private Double valor;
    private String observacao;
    private String status;

    @ManyToOne
    private Usuario id_Prestador;

    @ManyToOne
    private SolicitaServico id_SolicitaServico;

    public void setId(Long id) {
        this.id = id;
    }

    public void setId_Cliente(Usuario id_Cliente) {
        if(id_Cliente != null){
            this.id_Cliente = id_Cliente;
        }
    }

    public void setValor(Double valor) {
        if(valor == null) {
            this.valor = 0.0;
        }else{
            this.valor = valor;
        }
    }

    public void setObservacao(String observacao) {
        if (observacao != "" || observacao != null){
            this.observacao = observacao;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId_Prestador(Usuario id_Prestador) {
        if(id_Prestador != null){
            this.id_Prestador = id_Prestador;
        }
    }

    public void setId_SolicitaServico(SolicitaServico id_SolicitaServico) {
            this.id_SolicitaServico = id_SolicitaServico;
    }
}