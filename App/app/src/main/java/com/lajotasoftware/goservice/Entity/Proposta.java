package com.lajotasoftware.goservice.Entity;

public class Proposta {
    private Long id;
    private Usuario id_Cliente;
    private Double valor;
    private String observacao;
    private Character propostaAceita;
    private Usuario id_Prestador;
    private SolicitaServico id_SolicitaServico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(Usuario id_Cliente) {
        this.id_Cliente = id_Cliente;
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

    public Usuario getId_Prestador() {
        return id_Prestador;
    }

    public void setId_Prestador(Usuario id_Prestador) {
        this.id_Prestador = id_Prestador;
    }

    public SolicitaServico getId_SolicitaServico() {
        return id_SolicitaServico;
    }

    public void setId_SolicitaServico(SolicitaServico id_SolicitaServico) {
        this.id_SolicitaServico = id_SolicitaServico;
    }
}