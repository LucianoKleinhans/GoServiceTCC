package com.lajotasoftware.goservice.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SolicitaServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoria;
    private String descricaoSolicitacao;
    private Double valor;

    @ManyToOne
    private Usuario id_Cliente;
}
