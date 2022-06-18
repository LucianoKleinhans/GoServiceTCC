package testes;

import com.lajotasoftware.goservice.Entity.Proposta;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Entity.Usuario;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class PropostaUC {

    //UsuarioController usuario = new UsuarioController();
    @Test
    public void criaProposta(){//test unitario
        Proposta proposta = new Proposta();
        Usuario usuario = new Usuario();
        SolicitaServico solicitaServico = new SolicitaServico();

        proposta.setId(1L);

        //fk
        usuario.setId(1L);
        proposta.setId_Cliente(usuario);
        proposta.getId_Cliente().getId();
        //fk
        usuario.setId_Prestador("123adb");
        proposta.setId_Prestador(usuario);
        proposta.getId_Prestador().getId_Prestador();
        //fk
        proposta.setValor(100.00);
        proposta.setObservacao("Formatação sem backup");
        //fk
        solicitaServico.setId(1234L);
        proposta.setId_SolicitaServico(solicitaServico);
        proposta.getId_SolicitaServico().getId();

        Assertions.assertNotNull(proposta.getId_Cliente().getId(),"ID cliente null");
        Assertions.assertNotNull(proposta.getId_Prestador().getId(),"ID Prestador Null");
        Assertions.assertNotNull(proposta.getId_SolicitaServico().getId(),"ID Solicita Servico null");
        Assertions.assertNotNull(proposta.getObservacao(),"Observacao Vazia");
        Assertions.assertNotNull(proposta.getValor(),"Valor de serviço zerado");
    }

    @Test
    public void naoCriaProposta(){//test unitario
        Proposta proposta = new Proposta();
        Usuario usuario = new Usuario();
        SolicitaServico solicitaServico = new SolicitaServico();

        proposta.setId(1L);

        usuario.setId(null);
        proposta.setId_Cliente(usuario);
        proposta.getId_Cliente().getId();

        usuario.setId_Prestador("123adb");
        proposta.setId_Prestador(usuario);
        proposta.getId_Prestador().getId_Prestador();

        proposta.setValor(null);
        proposta.setObservacao("");

        solicitaServico.setId(null);
        proposta.setId_SolicitaServico(solicitaServico);
        proposta.getId_SolicitaServico().getId();

        Assertions.assertNotNull(proposta.getId_Cliente().getId(),"ID cliente null");
        Assertions.assertNotNull(proposta.getId_Prestador().getId(),"ID Prestador Null");
        Assertions.assertNotNull(proposta.getId_SolicitaServico().getId(),"ID Solicita Servico null");
        Assertions.assertNotNull(proposta.getObservacao(),"Observacao Vazia");
        Assertions.assertNotNull(proposta.getValor(),"Valor de serviço zerado");
    }

    @Test
    public void casoUsoSalvarComExito() throws JSONException, IOException {//teste integracao
        URL url = new URL ("http://localhost:9090/usuario/");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = "\"{\"\"id\"\":1,\"id_Prestador\":\"123abc\",\"nome\":\"Bruno Formiga\",\"cpf\":\"07464993101\",\"cnpj\":\"11162692000190\",\"endereco\":\"Bom dia ao lado do boa noite\",\"telefone\":\"67996002604\",\"email\":\"eobrunoformiga@gmail.com\",\"login\":\"formigabruno\",\"senha\":\"beemovieee\",\"avaliacaoPrestador\":5.0,\"avaliacaoCliente\":4.5,\"prestador\":true,\"ativo\":true}\"";

    }

}
