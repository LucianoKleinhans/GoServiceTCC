package com.lajotasoftware.goservice.Entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Character status;
    private Double avaliacaoPrestador;
    private Double avaliacaoCliente;
    private Character servicoSolicitado;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataEmissao;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataFinalizacao;

    @ManyToOne
    private Usuario id_Cliente;
    @ManyToOne
    private Usuario id_Prestador;
    @OneToOne
    private SolicitaServico id_ServicoSolicitado;
    @ManyToOne
    private Servico id_Servico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
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

    public Character getServicoSolicitado() {
        return servicoSolicitado;
    }

    public void setServicoSolicitado(Character servicoSolicitado) {
        this.servicoSolicitado = servicoSolicitado;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(Date dataFinalizacao) {
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
