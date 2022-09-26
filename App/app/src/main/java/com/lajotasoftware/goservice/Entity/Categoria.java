package com.lajotasoftware.goservice.Entity;

public class Categoria {

    private Long id;
    private String nome;

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

    @Override
    public String toString() {
        return id + " - " + nome;
    }

    public void setCategoria(Categoria categoria){
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    };

}
