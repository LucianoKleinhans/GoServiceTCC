package com.lajotasoftware.goservice.Entity;

public class Pedido {

    private Long id;
    private String status;
    private Double avaliacaoPrestador;
    private Double avaliacaoCliente;
    private Boolean servicoSolicitado;
    private Long dataEmissao;
    private Long dataFinalizacao;
    private Usuario id_Cliente;
    private Usuario id_Prestador;
    private SolicitaServico id_ServicoSolicitado;
    private Servico id_Servico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAvaliacaoPrestador() {
        return avaliacaoPrestador;
    }

    public void setAvaliacaoPrestador(Double avaliacaoPrestador) {
        this.avaliacaoPrestador = avaliacaoPrestador;
    }

    public Double getAvaliacaoCliente() {
        return avaliacaoCliente;
    }

    public void setAvaliacaoCliente(Double avaliacaoCliente) {
        this.avaliacaoCliente = avaliacaoCliente;
    }

    public Boolean getServicoSolicitado() {
        return servicoSolicitado;
    }

    public void setServicoSolicitado(Boolean servicoSolicitado) {
        this.servicoSolicitado = servicoSolicitado;
    }

    public Long getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Long dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Long getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(Long dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    public Usuario getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(Usuario id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public Usuario getId_Prestador() {
        return id_Prestador;
    }

    public void setId_Prestador(Usuario id_Prestador) {
        this.id_Prestador = id_Prestador;
    }

    public SolicitaServico getId_ServicoSolicitado() {
        return id_ServicoSolicitado;
    }

    public void setId_ServicoSolicitado(SolicitaServico id_ServicoSolicitado) {
        this.id_ServicoSolicitado = id_ServicoSolicitado;
    }

    public Servico getId_Servico() {
        return id_Servico;
    }

    public void setId_Servico(Servico id_Servico) {
        this.id_Servico = id_Servico;
    }

}