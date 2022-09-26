package com.lajotasoftware.goservice.Entity;

import android.annotation.SuppressLint;

import java.util.List;

public class Servico {
    private Long id;
    private String nome;
    private Categoria categoria;
    private SubCategoria subCategoria;
    private Double valor;
    private String obsServico;
    private Usuario id_Prestador;

    List<Servico> servicos;

    public void setServico(Servico servico){
        this.id = servico.getId();
        this.nome = servico.getNome();
        this.categoria = servico.getCategoria();
        this.subCategoria = servico.getSubCategoria();
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
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