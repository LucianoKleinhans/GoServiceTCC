/* package casodeusotest;

import com.lajotasoftware.goservice.DAO.DAOUsuario;
import com.lajotasoftware.goservice.Entity.Usuario;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UsuarioUC {

    @Test
    public void criaUsuario(){//t3est unitario
        Usuario usuario = new Usuario();
        usuario.setId(99l);
        usuario.setAtivo(null);
        Assertions.assertNotNull(usuario.getAtivo(),"teste bolean ativo");//
        usuario.setId_Prestador("45656");

        usuario.setCpfcnpj("9999999");//cab
    }
    @Test
    public void nãoCriaUsuario(){//t3est unitario
        Usuario usuario = new Usuario();
        usuario.setId(99l);
        usuario.setAtivo(null);
        Assertions.assertNotNull(usuario.getAtivo(),"teste bolean ativo");//

        usuario.setCpfcnpj("9999999");//cab

    }

    @Test
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

    }

}
*/