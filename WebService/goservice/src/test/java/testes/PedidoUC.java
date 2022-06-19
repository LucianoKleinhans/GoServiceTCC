package testes;

import com.lajotasoftware.goservice.Controller.PedidoController;
import com.lajotasoftware.goservice.Controller.ServicoController;
import com.lajotasoftware.goservice.Controller.SolicitaServicoController;
import com.lajotasoftware.goservice.Controller.UsuarioController;
import com.lajotasoftware.goservice.Entity.Pedido;
import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Entity.Usuario;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
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

        usuario.setId_Prestador(1L);
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

    /*TESTE INTEGRAÇÃO*/
    @Autowired
    public PedidoController pedidoController;
    @Autowired
    public UsuarioController usuarioController;
    @Autowired
    public ServicoController servicoController;
    @Autowired
    public SolicitaServicoController solicitaServicoController;
    @Test
    public void casoUsoSalva() {//teste integracao
        Pedido pedido = new Pedido();
        Usuario usuario = new Usuario();
        SolicitaServico solicitaServico = new SolicitaServico();
        Servico servico = new Servico();

        pedido.setId(1L);
        pedido.setStatus('A');
        pedido.setAvaliacaoPrestador(5.0);
        pedido.setAvaliacaoCliente(3.0);
        pedido.setDataEmissao(12312321321L);
        pedido.setDataFinalizacao(12312312344L);

        pedido.setId_Cliente(usuarioController.getUsuarioById(1L));
        pedido.setId_Prestador((Usuario) usuarioController.getPrestador(1L));
        pedido.setId_ServicoSolicitado(solicitaServicoController.getSolicitaServicoById(1L));
        pedido.setId_Servico(servicoController.getServicoById(1L));

        pedidoController.salvarPedido(pedido);
    }
}