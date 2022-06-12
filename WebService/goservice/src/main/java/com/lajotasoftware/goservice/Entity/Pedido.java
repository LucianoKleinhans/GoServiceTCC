package com.lajotasoftware.goservice.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Character status;
    private Double avaliacaoPrestador;
    private Double avaliacaoCliente;
    private Character servicoSolicitado;
    private Date dataEmissao;
    private Date dataFinalizacao;

    @OneToOne
    private Integer id_Cliente;
    @OneToOne
    private String id_Prestador;
    @OneToOne
    private Integer id_ServicoSolicitado;
    @OneToOne
    private Integer id_Servico;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(Integer id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public String getId_Prestador() {
        return id_Prestador;
    }

    public void setId_Prestador(String id_Prestador) {
        this.id_Prestador = id_Prestador;
    }

    public Integer getId_ServicoSolicitado() {
        return id_ServicoSolicitado;
    }

    public void setId_ServicoSolicitado(Integer id_ServicoSolicitado) {
        this.id_ServicoSolicitado = id_ServicoSolicitado;
    }

    public Integer getId_Servico() {
        return id_Servico;
    }

    public void setId_Servico(Integer id_Servico) {
        this.id_Servico = id_Servico;
    }
}
