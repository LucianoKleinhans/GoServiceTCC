package com.lajotasoftware.goservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String mensagem;

    private Long dataHoraMsg;

    private Long id_Cliente;
    private String id_Prestador;

    @JsonIgnore
    @ManyToOne
    private Proposta id_Proposta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Long getDataHoraMsg() {
        return dataHoraMsg;
    }

    public void setDataHoraMsg(Long dataHoraMsg) {
        this.dataHoraMsg = dataHoraMsg;
    }

    public Long getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(Long id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public String getId_Prestador() {
        return id_Prestador;
    }

    public void setId_Prestador(String id_Prestador) {
        this.id_Prestador = id_Prestador;
    }

    public Proposta getId_Proposta() {
        return id_Proposta;
    }

    public void setId_Proposta(Proposta id_Proposta) {
        this.id_Proposta = id_Proposta;
    }
}
