package com.lajotasoftware.goservice.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SolicitaServico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ID;

    private Integer ID_Cliente;
    private String Categoria;
    private String DescricaoSolicitacao;
    private Double Valor;


    private Integer ID_Proposta;
}
