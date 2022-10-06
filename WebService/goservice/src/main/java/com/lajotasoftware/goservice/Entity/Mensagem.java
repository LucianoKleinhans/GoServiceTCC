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

    @ManyToOne
    private Usuario id_Cliente;
    @ManyToOne
    private Usuario id_Prestador;

    @ManyToOne
    private Proposta id_Proposta;

    @ManyToOne
    private Usuario sendBy;

    public void setId(Long id) {
        this.id = id;
    }

    public void setMensagem(String mensagem) {
        if(mensagem != null && mensagem != ""){
            this.mensagem = mensagem;
        }
    }

    public void setDataHoraMsg(Long dataHoraMsg) {
        this.dataHoraMsg = dataHoraMsg;
    }

    public void setId_Cliente(Usuario id_Cliente) {
        if(id_Cliente != null){
        this.id_Cliente = id_Cliente;
        }
    }

    public void setId_Prestador(Usuario id_Prestador) {
        if(id_Prestador != null){
            this.id_Prestador = id_Prestador;
        }
    }

    public void setId_Proposta(Proposta id_Proposta) {
        if(id_Proposta != null){
            this.id_Proposta = id_Proposta;
        }
    }

    public void setSendBy(Usuario sendBy) {
        this.sendBy = sendBy;
    }
}
