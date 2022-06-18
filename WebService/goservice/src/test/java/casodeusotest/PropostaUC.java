package casodeusotest;

import com.lajotasoftware.goservice.Controller.UsuarioController;
import com.lajotasoftware.goservice.Entity.Proposta;
import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Entity.Usuario;
import org.apache.commons.validator.routines.EmailValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PropostaUC {

    //UsuarioController usuario = new UsuarioController();
    @Test
    public void criaProposta(){//test unitario
        Proposta proposta = new Proposta();
        Usuario usuario = new Usuario();
        SolicitaServico solicitaServico = new SolicitaServico();

        proposta.setId(1L);

        usuario.setId(1L);
        proposta.setId_Cliente(usuario);
        proposta.getId_Cliente().getId();

        usuario.setId_Prestador("123adb");
        proposta.setId_Prestador(usuario);
        proposta.getId_Prestador().getId_Prestador();

        proposta.setValor(100.00);
        proposta.setObservacao("Formatação sem backup");

        solicitaServico.setId(1234L);
        proposta.setId_SolicitaServico(solicitaServico);
        proposta.getId_SolicitaServico().getId();

        Assertions.assertNotNull(proposta.getValor(),"Valor de serviço zerado");
    }

    @Test
    public void naoCriaProposta(){//test unitario
        Proposta proposta = new Proposta();
        proposta.setId(null);
        proposta.setId_Cliente(null);
       // proposta.setId_Prestador(usuario.getUsuarioById(Long.valueOf("111")));
        proposta.setValor(100.00);
        proposta.setObservacao("");
        //proposta.setId_SolicitaServico();
    }

    /*@Test
    public void casoUsoSalvarComExito() throws JSONException {//teste integracao
        casodeuso.UsuarioUC uc = new casodeuso.UsuarioUC();
        JSONObject jsonObject = new JSONObject();

        //Armazena dados em um Objeto JSON
        jsonObject.put("nome", "Allan");
        jsonObject.put("sobrenome", "Romanato");
        jsonObject.put("pais", "Brasil");
        jsonObject.put("estado", "SP");

        uc.salvar(jsonObject);
    }


    @Test
    public void salvarComExito(){//teste integracao
        Assertions.assertDoesNotThrow(DAOUsuario.salvar(criaUsuario()));

    }
    @Test
    public void salvarSemExito(){//teste integracao
        Assertions.assertThrows(DAOUsuario.salvar(nãoCriaUsuario()));

    }*/
}
