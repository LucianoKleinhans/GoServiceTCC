package com.lajotasoftware.goservice.Entity;


import javax.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private String ID_Prestador;
    private String Nome;
    private String CPFCNPJ;
    private String Endereco;
    private String Telefone;
    private String Email;
    private String Login;
    private String Senha;
    private Double AvaliacaoPrestador;
    private Double AvaliacaoCliente;
    private Character Prestador;
    private Boolean Ativo;

    @OneToMany
    private Servico ID_CadServico;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getID_Prestador() {
        return ID_Prestador;
    }

    public void setID_Prestador(String ID_Prestador) {
        this.ID_Prestador = ID_Prestador;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCPFCNPJ() {
        return CPFCNPJ;
    }

    public void setCPFCNPJ(String CPFCNPJ) {
        this.CPFCNPJ = CPFCNPJ;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public Double getAvaliacaoPrestador() {
        return AvaliacaoPrestador;
    }

    public void setAvaliacaoPrestador(Double avaliacaoPrestador) {
        AvaliacaoPrestador = avaliacaoPrestador;
    }

    public Double getAvaliacaoCliente() {
        return AvaliacaoCliente;
    }

    public void setAvaliacaoCliente(Double avaliacaoCliente) {
        AvaliacaoCliente = avaliacaoCliente;
    }

    public Character getPrestador() {
        return Prestador;
    }

    public void setPrestador(Character prestador) {
        Prestador = prestador;
    }

    public Boolean getAtivo() {
        return Ativo;
    }

    public void setAtivo(Boolean ativo) {
        Ativo = ativo;
    }

    public Servico getID_Servico() {
        return ID_CadServico;
    }

    public void setID_CadServico(Servico ID_CadServico) {
        this.ID_CadServico = ID_CadServico;
    }
}
