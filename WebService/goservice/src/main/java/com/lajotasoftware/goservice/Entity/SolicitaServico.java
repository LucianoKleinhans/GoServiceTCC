package com.lajotasoftware.goservice.Entity;

import javax.persistence.*;

@Entity
public class SolicitaServico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String categoria;
    private String descricaoSolicitacao;
    private Double valor;

    @ManyToOne
    private Integer id_Cliente;

    @OneToMany
    private Integer id_Proposta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricaoSolicitacao() {
        return descricaoSolicitacao;
    }

    public void setDescricaoSolicitacao(String descricaoSolicitacao) {
        this.descricaoSolicitacao = descricaoSolicitacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(Integer id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public Integer getId_Proposta() {
        return id_Proposta;
    }

    public void setId_Proposta(Integer id_Proposta) {
        this.id_Proposta = id_Proposta;
    }
}
