package com.lajotasoftware.goservice.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String categoria;
    private String subCategoria;
    private Double valor;
    private String obsServico;

    @ManyToOne
    private Usuario id_Prestador;

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        if (nome != null && nome != ""){
            this.nome = nome;
        }
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setObsServico(String obsServico) {
        this.obsServico = obsServico;
    }

    public void setId_Prestador(Usuario id_Prestador) {
        this.id_Prestador = id_Prestador;
    }
}