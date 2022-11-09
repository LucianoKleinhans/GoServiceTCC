package com.lajotasoftware.goservice.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private Double avaliacaoPrestador;
    private Double avaliacaoCliente;
    private Boolean servicoSolicitado;
    private Long dataEmissao;
    private Long dataFinalizacao;

    @ManyToOne
    private Usuario id_Cliente;
    @ManyToOne
    private Usuario id_Prestador;
    @OneToOne
    private SolicitaServico id_ServicoSolicitado;
    @ManyToOne
    private Servico id_Servico;

    @OneToOne
    private Proposta id_Proposta;

    public void setId(Long id) {
        if (id != null) {
            this.id = id;
        }
    }

    public void setStatus(String status) {
        if (status != null) {
            this.status = status.toUpperCase();
        }
    }

    public void setAvaliacaoPrestador(Double avaliacaoPrestador) {
        if (avaliacaoPrestador >= 0.0 && avaliacaoPrestador <=5.0) {
            this.avaliacaoPrestador = avaliacaoPrestador;
        }else if (avaliacaoPrestador==null){
            this.avaliacaoPrestador = 0.0;
        }
    }

    public void setAvaliacaoCliente(Double avaliacaoCliente) {
        if (avaliacaoCliente >= 0.0 && avaliacaoCliente <=5.0) {
            this.avaliacaoCliente = avaliacaoCliente;
        }else if (avaliacaoCliente==null){
            this.avaliacaoCliente = 0.0;
        }
    }

    public void setServicoSolicitado(Boolean servicoSolicitado) {
        if (servicoSolicitado != null) {
            this.servicoSolicitado = servicoSolicitado;
        }
    }

    public void setDataEmissao(Long dataEmissao) {
        if (dataEmissao != null) {
            this.dataEmissao = dataEmissao;
        }
    }

    public void setDataFinalizacao(Long dataFinalizacao) {
        if (dataFinalizacao != null) {
            this.dataFinalizacao = dataFinalizacao;
        }
    }

    public void setId_Cliente(Usuario id_Cliente) {
        if (id_Cliente != null) {
            this.id_Cliente = id_Cliente;
        }
    }

    public void setId_Prestador(Usuario id_Prestador) {
        if (id_Prestador != null) {
            this.id_Prestador = id_Prestador;
        }
    }

    public void setId_ServicoSolicitado(SolicitaServico id_ServicoSolicitado) {
        if (id_ServicoSolicitado != null) {
            this.id_ServicoSolicitado = id_ServicoSolicitado;
        }
    }

    public void setId_Servico(Servico id_Servico) {
        if (id_Servico != null) {
            this.id_Servico = id_Servico;
        }
    }

    public void setId_Proposta(Proposta id_Proposta) {
        if (id_Proposta != null) {
            this.id_Proposta = id_Proposta;
        }
    }
}
