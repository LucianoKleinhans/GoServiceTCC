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
}
