package com.lajotasoftware.goservice.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ID;
    private String Nome;
    private String Categoria;
    private String SubCategoria;
    private Double Valor;
    private String ObsServico;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getSubCategoria() {
        return SubCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        SubCategoria = subCategoria;
    }

    public Double getValor() {
        return Valor;
    }

    public void setValor(Double valor) {
        Valor = valor;
    }

    public String getObsServico() {
        return ObsServico;
    }

    public void setObsServico(String obsServico) {
        ObsServico = obsServico;
    }
}
