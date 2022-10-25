package com.lajotasoftware.goservice.Entity;

public class SubCategoria {

    private Long id;
    private String nome;
    private Categoria idCategoriaServico;

    public void setSubCategoria(SubCategoria subCategoria){
        this.id = subCategoria.getId();
        this.nome = subCategoria.getNome();
        this.idCategoriaServico = subCategoria.getIdCategoriaServico();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria getIdCategoriaServico() {
        return idCategoriaServico;
    }

    public void setIdCategoriaServico(Categoria idCategoriaServico) {
        this.idCategoriaServico = idCategoriaServico;
    }

    @Override
    public String toString() {
        return id+" - "+nome;
    }
}
