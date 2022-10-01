package com.lajotasoftware.goservice.Entity;

import java.util.List;

public class SolicitaServico {
    private Long id;
    private String nomeServico;
    private Categoria id_Categoria;
    private SubCategoria id_SubCategoria;
    private String descricaoSolicitacao;
    private Double valor;
    private Boolean excluido;
    private Double valorProposto;
    private Usuario id_Cliente;

    List<SolicitaServico> cardsServicos;

    public void setServico(SolicitaServico cardsServicos){
        this.id = cardsServicos.getId();
        this.nomeServico = cardsServicos.getNomeServico();
        this.id_Categoria = cardsServicos.getId_Categoria();
        this.id_SubCategoria = cardsServicos.getId_SubCategoria();
        this.valor = cardsServicos.getValor();
        this.excluido = cardsServicos.getExcluido();
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

    public Categoria getId_Categoria() {
        return id_Categoria;
    }

    public void setId_Categoria(Categoria id_Categoria) {
        this.id_Categoria = id_Categoria;
    }

    public SubCategoria getId_SubCategoria() {
        return id_SubCategoria;
    }

    public void setId_SubCategoria(SubCategoria id_SubCategoria) {
        this.id_SubCategoria = id_SubCategoria;
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

    public Boolean getExcluido() {
        return excluido;
    }

    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
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
        return  id.toString() + '\n' +
                nomeServico + '\n' +
                descricaoSolicitacao + '\n' +
                "R$"+valor;
    }
}