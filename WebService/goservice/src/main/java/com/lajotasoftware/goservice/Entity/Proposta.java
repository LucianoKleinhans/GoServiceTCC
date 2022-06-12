package com.lajotasoftware.goservice.Entity;

import javax.persistence.*;

@Entity
public class Proposta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Double valor;
    private String observacao;
    private Character propostaAceita;
    private Integer propostaMensagem;

    @ManyToOne
    private Usuario id_Prestador;
    @ManyToOne
    private Usuario id_Cliente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Character getPropostaAceita() {
        return propostaAceita;
    }

    public void setPropostaAceita(Character propostaAceita) {
        this.propostaAceita = propostaAceita;
    }

    public Integer getPropostaMensagem() {
        return propostaMensagem;
    }

    public void setPropostaMensagem(Integer propostaMensagem) {
        this.propostaMensagem = propostaMensagem;
    }

    public Usuario getId_Prestador() {
        return id_Prestador;
    }

    public void setId_Prestador(Usuario id_Prestador) {
        this.id_Prestador = id_Prestador;
    }

    public Usuario getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(Usuario id_Cliente) {
        this.id_Cliente = id_Cliente;
    }
}