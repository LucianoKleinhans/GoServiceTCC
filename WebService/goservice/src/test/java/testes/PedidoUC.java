package testes;

import com.lajotasoftware.goservice.Entity.Pedido;
import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Entity.Usuario;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

public class PedidoUC {

    @Test
    public void CriaPedido() {//test unitario
        Pedido pedido = new Pedido();
        Usuario usuario = new Usuario();
        SolicitaServico solicitaServico = new SolicitaServico();
        Servico servico = new Servico();

        pedido.setId(99L);
        pedido.setStatus('A');
        pedido.setAvaliacaoPrestador(4.50);
        pedido.setAvaliacaoCliente(1.50);
        pedido.setServicoSolicitado(true);
        pedido.setDataEmissao(1655576721686L);
        pedido.setDataFinalizacao(1655576730478L);

        usuario.setId(1L);
        pedido.setId_Cliente(usuario);//id_Cliente
        pedido.getId_Cliente().getId();

        usuario.setId_Prestador("123adb");
        pedido.setId_Prestador(usuario);//id_Prestador
        pedido.getId_Prestador().getId_Prestador();

        solicitaServico.setId(1L);
        pedido.setId_ServicoSolicitado(solicitaServico);//id_ServicoSolicitado
        pedido.getId_ServicoSolicitado().getId();

        servico.setId(1L);
        pedido.setId_Servico(servico);//id_Servico
        pedido.getId_Servico().getId();

        Assertions.assertNotNull(pedido.getId(), "ID invalido");
        Assertions.assertNotNull(pedido.getStatus(), "Status Invalido");
        Assertions.assertNotNull(pedido.getAvaliacaoPrestador(), "Avaliacao invalida");
        Assertions.assertNotNull(pedido.getAvaliacaoCliente(), "Avaliacao invalida");
        Assertions.assertNotNull(pedido.getDataEmissao(), "Data Emissao inccorreta");
        Assertions.assertNotNull(pedido.getDataFinalizacao(), "Data Finalizacao incorreta");
        Assertions.assertNotNull(pedido.getId_Cliente(), "Id Cliente incorreto");
        Assertions.assertNotNull(pedido.getId_Prestador(), "Id Prestador Incorreto");
    }

    @Test
    public void naoCriaPedido() {//test unitario
        Pedido pedido = new Pedido();
        Usuario usuario = new Usuario();
        SolicitaServico solicitaServico = new SolicitaServico();
        Servico servico = new Servico();

        pedido.setId(null);
        pedido.setStatus(null);
        pedido.setAvaliacaoPrestador(9.0);
        pedido.setAvaliacaoCliente(50.0);
        pedido.setServicoSolicitado(true);

        // Não ira precisar tratar as dastas na entidade pois ela vai ser tratada diretamente no componete.
        pedido.setDataEmissao(1655576721686L);
        pedido.setDataFinalizacao(1655576721686L);

        usuario.setId(null);
        pedido.setId_Cliente(usuario);//id_Cliente
        pedido.getId_Cliente().getId();

        usuario.setId_Prestador(null);
        pedido.setId_Prestador(usuario);//id_Prestador
        pedido.getId_Prestador().getId_Prestador();

        solicitaServico.setId(null);
        pedido.setId_ServicoSolicitado(solicitaServico);//id_ServicoSolicitado
        pedido.getId_ServicoSolicitado().getId();

        servico.setId(null);
        pedido.setId_Servico(servico);//id_Servico
        pedido.getId_Servico().getId();

        Assertions.assertNotNull(pedido.getId(), "ID invalido");
        Assertions.assertNotNull(pedido.getStatus(), "Status Invalido");
        Assertions.assertNotNull(pedido.getAvaliacaoPrestador(), "Avaliacao invalida");
        Assertions.assertNotNull(pedido.getAvaliacaoCliente(), "Avaliacao invalida");
        Assertions.assertNotNull(pedido.getDataEmissao(), "Data Emissao inccorreta");
        Assertions.assertNotNull(pedido.getDataFinalizacao(), "Data Finalizacao incorreta");
        Assertions.assertNotNull(pedido.getId_Cliente(), "Id Cliente incorreto");
        Assertions.assertNotNull(pedido.getId_Prestador(), "Id Prestador Incorreto");

    }
}