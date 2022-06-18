package testes;

import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Entity.Usuario;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

public class SolicitaServicoUC {
    @Test
    public void CriaSolicitaServico() {//test unitario
        SolicitaServico solicitaServicoServico = new SolicitaServico();
        Usuario usuario = new Usuario();

        solicitaServicoServico.setId(1L);

        //Categoria sera tratada diretamente no front, Não será possivel selecionar valores invalidos.
        solicitaServicoServico.setCategoria("Manutenção");
        solicitaServicoServico.setSubCategoria("Informática");

        solicitaServicoServico.setDescricaoSolicitacao("Faço formatação com backup em qualquer dia da semana");
        solicitaServicoServico.setValor(100.00);
        //FK
        usuario.setId(1L);
        solicitaServicoServico.setId_Cliente(usuario);//id_Prestador
        solicitaServicoServico.getId_Cliente().getId();

        Assertions.assertNotNull(solicitaServicoServico.getId(), "ID incorreto");
        Assertions.assertNotNull(solicitaServicoServico.getCategoria(), "Categoria incorreta");
        Assertions.assertNotNull(solicitaServicoServico.getSubCategoria(), "Sub Categoria incorreta");
        Assertions.assertNotNull(solicitaServicoServico.getDescricaoSolicitacao(), "Obs incorreta");
        Assertions.assertNotNull(solicitaServicoServico.getValor(), "Valor incorreto");
        Assertions.assertNotNull(solicitaServicoServico.getId_Cliente(), "Id Prestador Incorreto");
    }
    @Test
    public void naoCriaSolicitaServico() {//test unitario
        SolicitaServico solicitaServicoServico = new SolicitaServico();
        Usuario usuario = new Usuario();

        solicitaServicoServico.setId(null);

        //Categoria sera tratada diretamente no front, Não será possivel selecionar valores invalidos.
        solicitaServicoServico.setCategoria("");
        solicitaServicoServico.setSubCategoria(null);

        solicitaServicoServico.setDescricaoSolicitacao("Faço formatação com backup em qualquer dia da semana");
        solicitaServicoServico.setValor(-50.00);
        //FK
        usuario.setId(null);
        solicitaServicoServico.setId_Cliente(usuario);//id_Prestador
        solicitaServicoServico.getId_Cliente().getId();

        Assertions.assertNotNull(solicitaServicoServico.getId(), "ID incorreto");
        Assertions.assertNotNull(solicitaServicoServico.getCategoria(), "Categoria incorreta");
        Assertions.assertNotNull(solicitaServicoServico.getSubCategoria(), "Sub Categoria incorreta");
        Assertions.assertNotNull(solicitaServicoServico.getDescricaoSolicitacao(), "Obs incorreta");
        Assertions.assertNotNull(solicitaServicoServico.getValor(), "Valor incorreto");
        Assertions.assertNotNull(solicitaServicoServico.getId_Cliente(), "Id Prestador Incorreto");
    }
}
