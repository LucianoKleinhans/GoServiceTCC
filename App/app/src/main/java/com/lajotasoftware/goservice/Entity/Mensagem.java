package com.lajotasoftware.goservice.Entity;

public class Mensagem {
    private Long id;
    private String mensagem;
    private Long dataHoraMsg;
    private Usuario id_Cliente;
    private Usuario id_Prestador;
    private Proposta id_Proposta;

    public void setMessagem(Mensagem mensagem){
        this.id = mensagem.getId();
        this.mensagem = mensagem.getMensagem();
        this.dataHoraMsg = mensagem.getDataHoraMsg();
        this.id_Cliente = mensagem.getId_Cliente();
        this.id_Prestador = mensagem.getId_Prestador();
        this.id_Proposta = mensagem.getId_Proposta();
    };

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

    public Usuario getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(Usuario id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public Usuario getId_Prestador() {
        return id_Prestador;
    }

    public void setId_Prestador(Usuario id_Prestador) {
        this.id_Prestador = id_Prestador;
    }

    public Proposta getId_Proposta() {
        return id_Proposta;
    }

    public void setId_Proposta(Proposta id_Proposta) {
        this.id_Proposta = id_Proposta;
    }
}