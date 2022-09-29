package com.lajotasoftware.goservice.Entity;

import android.annotation.SuppressLint;

import java.util.List;

public class Servico {
    private Long id;
    private String nome;
    private Categoria id_Categoria;
    private SubCategoria id_SubCategoria;
    private Double valor;
    private String obsServico;
    private Usuario id_Prestador;

    List<Servico> servicos;

    public void setServico(Servico servico){
        this.id = servico.getId();
        this.nome = servico.getNome();
        this.id_Categoria = servico.getId_Categoria();
        this.id_SubCategoria = servico.getId_SubCategoria();
        this.valor = servico.getValor();
        this.obsServico = servico.getObsServico();
        this.id_Prestador = servico.getId_Prestador();
    };
    
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getObsServico() {
        return obsServico;
    }

    public void setObsServico(String obsServico) {
        this.obsServico = obsServico;
    }

    public Usuario getId_Prestador() {
        return id_Prestador;
    }

    public void setId_Prestador(Usuario id_Prestador) {
        this.id_Prestador = id_Prestador;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return  id.toString() + '\n' +
                nome + '\n' +
                obsServico + '\n' +
                "R$"+valor;
    }

}