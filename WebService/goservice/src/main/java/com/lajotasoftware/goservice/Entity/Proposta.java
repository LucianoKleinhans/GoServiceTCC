package com.lajotasoftware.goservice.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Proposta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long id_Cliente;
    private Double valor;
    private String observacao;
    private Character propostaAceita;
    private Long propostaMensagem;

    @ManyToOne
    private Usuario id_Prestador;

    @ManyToOne
    private SolicitaServico id_SolicitaServico;

}