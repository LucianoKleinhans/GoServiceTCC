package com.lajotasoftware.goservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;

    private Long dataHoraMsg;

    private Long id_Cliente;
    private String id_Prestador;

    @JsonIgnore
    @ManyToOne
    private Proposta id_Proposta;
}
