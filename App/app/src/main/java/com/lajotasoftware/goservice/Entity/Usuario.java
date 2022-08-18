package com.lajotasoftware.goservice.Entity;

public class Usuario {
    private Long id;
    private Long id_Prestador;
    private String primeiroNome;
    private String segundoNome;
    private String cpf;
    private String cnpj;
    private String ruaAvenida;
    private String bairro;
    private String numero;
    private String cep;
    private String cidade;
    private String uf;
    private String telefone;
    private String email;
    private String site;
    private String login;
    private String senha;
    private Double avaliacaoPrestador;
    private Double avaliacaoCliente;
    private Boolean prestador;
    private Boolean ativo;

    public void setUsuario(Usuario usuario){
        this.id = usuario.getId();
        this.id_Prestador = usuario.getId_Prestador();
        this.primeiroNome = usuario.getPrimeiroNome();
        this.segundoNome = usuario.getSegundoNome();
        this.cpf = usuario.getCpf();
        this.cnpj = usuario.getCnpj();
        this.ruaAvenida = usuario.getRuaAvenida();
        this.bairro = usuario.getBairro();
        this.numero = usuario.getNumero();
        this.cep = usuario.getCep();
        this.cidade = usuario.getCidade();
        this.uf = usuario.getUf();
        this.telefone = usuario.getTelefone();
        this.email = usuario.getEmail();
        this.site = usuario.getSite();
        this.login = usuario.getLogin();
        this.senha = usuario.getSenha();
        this.avaliacaoPrestador = usuario.getAvaliacaoPrestador();
        this.avaliacaoCliente = usuario.getAvaliacaoCliente();
        this.prestador = usuario.getPrestador();
        this.ativo = usuario.getAtivo();
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_Prestador() {
        return id_Prestador;
    }

    public void setId_Prestador(Long id_Prestador) {
        this.id_Prestador = id_Prestador;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getSegundoNome() {
        return segundoNome;
    }

    public void setSegundoNome(String segundoNome) {
        this.segundoNome = segundoNome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRuaAvenida() {
        return ruaAvenida;
    }

    public void setRuaAvenida(String ruaAvenida) {
        this.ruaAvenida = ruaAvenida;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
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

    public Boolean getPrestador() {
        return prestador;
    }

    public void setPrestador(Boolean prestador) {
        this.prestador = prestador;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}