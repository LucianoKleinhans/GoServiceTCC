package com.lajotasoftware.goservice.Entity;


import javax.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String id_Prestador;
    private String nome;
    private String cpfcnpj;
    private String endereco;
    private String telefone;
    private String email;
    private String login;
    private String senha;
    private Double avaliacaoPrestador;
    private Double avaliacaoCliente;
    private Character prestador;
    private Boolean ativo;

    @OneToMany
    private Servico id_CadServico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_Prestador() {
        return id_Prestador;
    }

    public void setId_Prestador(String id_Prestador) {
        this.id_Prestador = id_Prestador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Double getAvaliacaoPrestador() {
        return avaliacaoPrestador;
    }

    public void setAvaliacaoPrestador(Double avaliacaoPrestador) {
        this.avaliacaoPrestador = avaliacaoPrestador;
    }

    public Double getAvaliacaoCliente() {
        return avaliacaoCliente;
    }

    public void setAvaliacaoCliente(Double avaliacaoCliente) {
        this.avaliacaoCliente = avaliacaoCliente;
    }

    public Character getPrestador() {
        return prestador;
    }

    public void setPrestador(Character prestador) {
        this.prestador = prestador;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Servico getId_CadServico() {
        return id_CadServico;
    }

    public void setId_CadServico(Servico id_CadServico) {
        this.id_CadServico = id_CadServico;
    }
}
