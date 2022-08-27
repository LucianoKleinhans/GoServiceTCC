package testes;

import com.lajotasoftware.goservice.Controller.MensagemController;
import com.lajotasoftware.goservice.Controller.PedidoController;
import com.lajotasoftware.goservice.Controller.PropostaController;
import com.lajotasoftware.goservice.Controller.UsuarioController;
import com.lajotasoftware.goservice.Entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MensagemUC {
    @Test
    public void criaMensagem(){//test unitario
        Mensagem mensagem = new Mensagem();
        Proposta proposta = new Proposta();
        Usuario usuario = new Usuario();

        //fk
        proposta.setId(1L);
        mensagem.setId_Proposta(proposta);
        mensagem.getId_Proposta().getId();
        mensagem.getId_Proposta().getValor();
        mensagem.getId_Proposta().getObservacao();
        //fk
        usuario.setId(1L);
        mensagem.setId_Cliente(usuario);
        mensagem.getId_Cliente().getId();
        //Fk
        usuario.setId_Prestador(null);
        mensagem.setId_Prestador(usuario);
        mensagem.getId_Prestador().getId_Prestador();

        mensagem.setId(1234L);
        mensagem.setMensagem("aceita ai verme");
        mensagem.setDataHoraMsg(1655583034248L);

        Assertions.assertNotNull(mensagem.getId(),"ID de mensagem incorreto");
        Assertions.assertNotNull(mensagem.getMensagem(),"Mensagem invalida ou vazia");
        Assertions.assertNotNull(mensagem.getDataHoraMsg(),"Horario invalido");
        Assertions.assertNotNull(mensagem.getId_Proposta().getId(),"ID de prosposta invalido");
        Assertions.assertNotNull(mensagem.getId_Proposta().getValor(),"Valor invalido");
        Assertions.assertNotNull(mensagem.getId_Proposta().getObservacao(),"Observacao vazia");
        Assertions.assertNotNull(mensagem.getId_Cliente().getId(),"ID de cliente invalido");
        Assertions.assertNotNull(mensagem.getId_Prestador().getId(),"ID de prestador invalido");

    }

    @Test
    public void naoCriaMensagem(){//test unitario
        Mensagem mensagem = new Mensagem();
        Proposta proposta = new Proposta();
        Usuario usuario = new Usuario();

        //fk
        proposta.setId(null);
        mensagem.setId_Proposta(proposta);
        mensagem.getId_Proposta().getId();
        mensagem.getId_Proposta().getValor();
        mensagem.getId_Proposta().getObservacao();
        //fk
        usuario.setId(null);
        mensagem.setId_Cliente(usuario);
        mensagem.getId_Cliente().getId();
        //Fk
        usuario.setId_Prestador(null);
        mensagem.setId_Prestador(usuario);
        mensagem.getId_Prestador().getId_Prestador();

        mensagem.setId(null);
        mensagem.setMensagem("");
        mensagem.setDataHoraMsg(null);

        Assertions.assertNotNull(mensagem.getMensagem(),"Mensagem invalida ou vazia");
        Assertions.assertNotNull(mensagem.getDataHoraMsg(),"Horario invalido");
        Assertions.assertNotNull(mensagem.getId_Proposta().getId(),"ID de prosposta invalido");
        Assertions.assertNotNull(mensagem.getId_Proposta().getValor(),"Valor invalido");
        Assertions.assertNotNull(mensagem.getId_Proposta().getObservacao(),"Observacao vazia");
        Assertions.assertNotNull(mensagem.getId_Cliente().getId(),"ID de cliente invalido");
        Assertions.assertNotNull(mensagem.getId_Prestador().getId(),"ID de prestador invalido");
    }

    /*TESTE INTEGRAÇÃO*/
    @Autowired
    public MensagemController mensagemController;
    @Autowired
    public UsuarioController usuarioController;
    @Autowired
    public PropostaController propostaController;
    @Test
    public void casoUsoSalva() {//teste integracao
        Mensagem mensagem = new Mensagem();
        Usuario usuario = new Usuario();
        Proposta proposta = new Proposta();

        mensagem.setId(1L);
        mensagem.setMensagem("abc1123123213");
        mensagem.setDataHoraMsg(1231232312L);

        //mensagem.setId_Cliente(usuarioController.getUsuarioById(1L));
        mensagem.setId_Prestador((Usuario) usuarioController.getPrestador(1L));
        mensagem.setId_Proposta(propostaController.getPropostaById(1L));

        mensagemController.salvarMensagem(mensagem);
    }
}
