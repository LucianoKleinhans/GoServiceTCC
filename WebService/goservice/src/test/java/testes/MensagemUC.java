package testes;

import com.lajotasoftware.goservice.Entity.Mensagem;
import com.lajotasoftware.goservice.Entity.Proposta;
import com.lajotasoftware.goservice.Entity.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        usuario.setId_Prestador("123abc");
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
        usuario.setId_Prestador("123abc");
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
}
