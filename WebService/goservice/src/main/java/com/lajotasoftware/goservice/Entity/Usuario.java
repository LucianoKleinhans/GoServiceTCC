package com.lajotasoftware.goservice.Entity;


import com.lajotasoftware.goservice.sources.Validacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String id_Prestador;
    private String nome;
    private String cpf;
    private String cnpj;
    private String endereco;
    private String telefone;
    private String email;
    private String login;
    private String senha;
    private Double avaliacaoPrestador;
    private Double avaliacaoCliente;
    private Boolean prestador;
    private Boolean ativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if(id != null){
            this.id = id;
        }
    }

    public String getId_Prestador() {
        return id_Prestador;
    }

    public void setId_Prestador(String id_Prestador) {
        if(id_Prestador!=null){
            this.id_Prestador = id_Prestador;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome!=null){
            this.nome = nome;
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        Validacao validacao = new Validacao();
        if(validacao.isCPF(cpf)){
            this.cpf = cpf;
        }
    }
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        Validacao validacao = new Validacao();
        if(validacao.isCNPJ(cnpj)){
            this.cnpj = cnpj;
        }
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
        Validacao validacao = new Validacao();
        if(validacao.validarTelefone(telefone)){
            this.telefone = telefone;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (EmailValidator.getInstance().isValid(email)){
            this.email = email;
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if(login.length()>=10){
            this.login = login;
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if(senha.length()>=10){
            this.senha = senha;
        }
    }

    public Double getAvaliacaoPrestador() {
        return avaliacaoPrestador;
    }

    public void setAvaliacaoPrestador(Double avaliacaoPrestador) {
        if(avaliacaoPrestador >= 0.0 && avaliacaoPrestador <= 5.0){
            this.avaliacaoPrestador = avaliacaoPrestador;
        }
    }

    public Double getAvaliacaoCliente() {
        return avaliacaoCliente;
    }

    public void setAvaliacaoCliente(Double avaliacaoCliente) {
        if(avaliacaoCliente >= 0.0 && avaliacaoCliente <= 5.0){
            this.avaliacaoCliente = avaliacaoCliente;
        }
    }

    public Boolean getPrestador() {
        return prestador;
    }

    public void setPrestador(Boolean prestador) {
        if(prestador == null|| prestador == false){
            this.prestador = false;
        }else{
            this.prestador = true;
        }
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo==null?false:ativo;
    }
}
