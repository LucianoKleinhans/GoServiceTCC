package com.lajotasoftware.goservice.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SolicitaServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeServico;

    @ManyToOne
    private Categoria id_Categoria;

    @ManyToOne
    private SubCategoria id_SubCategoria;
    private String descricaoSolicitacao;
    private Double valor;

    private Boolean excluido;
    private Double valorProposto;

    @ManyToOne
    private Usuario id_Cliente;

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public void setId_Categoria(Categoria id_Categoria) {
        this.id_Categoria = id_Categoria;
    }

    public void setId_SubCategoria(SubCategoria id_SubCategoria) {
        this.id_SubCategoria = id_SubCategoria;
    }

    public void setDescricaoSolicitacao(String descricaoSolicitacao) {
        this.descricaoSolicitacao = descricaoSolicitacao;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public void setValorProposto(Double valorProposto) {
        this.valorProposto = valorProposto;
    }

    public void setId_Cliente(Usuario id_Cliente) {
        this.id_Cliente = id_Cliente;
    }
}
