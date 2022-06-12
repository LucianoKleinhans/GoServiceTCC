package com.lajotasoftware.goservice.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String mensagem;
    private Date dataHoraMsg;

    private Integer id_Cliente;
    private String id_Prestador;
    private Integer id_Proposta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getDataHoraMsg() {
        return dataHoraMsg;
    }

    public void setDataHoraMsg(Date dataHoraMsg) {
        this.dataHoraMsg = dataHoraMsg;
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

    public Integer getId_Proposta() {
        return id_Proposta;
    }

    public void setId_Proposta(Integer id_Proposta) {
        this.id_Proposta = id_Proposta;
    }
}
