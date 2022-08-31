package com.lajotasoftware.goservice.Entity;

import java.util.List;

public class SolicitaServico {
    private Long id;
    private String nomeServico;
    private String categoria;
    private String subCategoria;
    private String descricaoSolicitacao;
    private Double valor;
    private Double valorProposto;
    private Usuario id_Cliente;

    List<SolicitaServico> cardsServicos;

    public void setServico(SolicitaServico cardsServicos){
        this.id = cardsServicos.getId();
        this.nomeServico = cardsServicos.getNomeServico();
        this.categoria = cardsServicos.getCategoria();
        this.subCategoria = cardsServicos.getSubCategoria();
        this.valor = cardsServicos.getValor();
        this.descricaoSolicitacao = cardsServicos.getDescricaoSolicitacao();
        this.valorProposto = cardsServicos.getValorProposto();
        this.id_Cliente = cardsServicos.getId_Cliente();
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
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

    public Double getValorProposto() {
        return valorProposto;
    }

    public void setValorProposto(Double valorProposto) {
        this.valorProposto = valorProposto;
    }

    public Usuario getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(Usuario id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    @Override
    public String toString() {
        return  nomeServico + '\n' +
                descricaoSolicitacao + '\n' +
                "R$"+valor;
    }
}