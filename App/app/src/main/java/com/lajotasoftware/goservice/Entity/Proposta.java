package com.lajotasoftware.goservice.Entity;

public class Proposta {
    private Long id;
    private Usuario id_Cliente;
    private Double valor;
    private String observacao;
    private String status;
    private Usuario id_Prestador;
    private SolicitaServico id_SolicitaServico;

    public void setProposta(Proposta proposta){
        this.id = proposta.getId();
        this.id_Cliente = proposta.getId_Cliente();
        this.valor = proposta.getValor();
        this.observacao = proposta.getObservacao();
        this.status = proposta.getStatus();
        this.id_Prestador = proposta.getId_Prestador();
        this.id_SolicitaServico = proposta.getId_SolicitaServico();
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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