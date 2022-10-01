package com.lajotasoftware.goservice.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToOne
    private Categoria id_Categoria;

    @ManyToOne
    private SubCategoria id_SubCategoria;
    private Double valor;

    private Boolean excluido;
    private String obsServico;

    @ManyToOne
    private Usuario id_Prestador;

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        if (nome != null && nome != ""){
            this.nome = nome;
        }
    }

    public void setId_Categoria(Categoria id_Categoria) {
        this.id_Categoria = id_Categoria;
    }

    public void setId_SubCategoria(SubCategoria id_SubCategoria) {
        this.id_SubCategoria = id_SubCategoria;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }


    public void setObsServico(String obsServico) {
        this.obsServico = obsServico;
    }

    public void setId_Prestador(Usuario id_Prestador) {
        this.id_Prestador = id_Prestador;
    }


}