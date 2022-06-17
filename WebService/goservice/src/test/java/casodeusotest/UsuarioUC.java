package casodeusotest;

import com.lajotasoftware.goservice.DAO.DAOUsuario;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.sources.Validacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

 public class UsuarioUC {
     Validacao validacao = new Validacao();
    @Test
    public void criaUsuario(){//test unitario
        Usuario usuario = new Usuario();
        String regexPattern = "^(.+)@(\\S+)$";
        usuario.setId(99L);
        usuario.setId_Prestador("45656");
        usuario.setNome("Ricardo Milos");
        usuario.setCpf("07464993101");
        Assertions.assertNotNull(usuario.getCpf(),"CPF INVALIDO");//
        usuario.setCnpj("11.162.692/0001-90");
        Assertions.assertNotNull(usuario.getCnpj(),"CNPJ INVALIDO");
        usuario.setEndereco("Bom dia ao lado do boa noite");
        usuario.setTelefone("67996002604");
        Assertions.assertNotNull(usuario.getTelefone(),"Telefone Invalido");
        usuario.setEmail("ricadola@gmail.com");
        Assertions.assertNotNull(usuario.getEmail(),"Email Invalido");
        usuario.setLogin("alojorgeemateus");
        Assertions.assertNotNull(usuario.getLogin(),"Login Invalido");
        usuario.setSenha("123asdqwe123");
        Assertions.assertNotNull(usuario.getSenha(),"Senha Invalido");
        usuario.setAvaliacaoPrestador(4.0);//Sem necessidade de testar pois vai ser feito o controle no front
        Assertions.assertNotNull(usuario.getAvaliacaoPrestador(),"Avaliacao invalida");
        usuario.setAvaliacaoCliente(4.0);//Sem necessidade de testar pois vai ser feito o controle no front
        Assertions.assertNotNull(usuario.getAvaliacaoCliente(),"Avaliacao invalida");
        usuario.setPrestador(true);
        Assertions.assertNotNull(usuario.getPrestador(),"teste bolean prestador ativo");
        usuario.setAtivo(true);
        Assertions.assertNotNull(usuario.getAtivo(),"teste bolean ativo");//
    }

    @Test
    public void naoCriaUsuario(){//test unitario
        Usuario usuario = new Usuario();
        usuario.setId(99L);
        usuario.setId_Prestador("45656");
        usuario.setNome("Ricardo Milos");
        usuario.setCpf("000.000.000-00");
        usuario.setCnpj("00.000.000/0000-00");
        usuario.setEndereco("Bom dia ao lado do boa noite");
        usuario.setTelefone("67 67894-5465");
        usuario.setEmail("ricadogmail.com");
        usuario.setLogin("alo");
        usuario.setSenha("123asdqwe");
        usuario.setAvaliacaoPrestador(8.0);//Sem necessidade de testar pois vai ser feito o controle no front
        usuario.setAvaliacaoCliente(9.0);//Sem necessidade de testar pois vai ser feito o controle no front
        usuario.setPrestador(null);//validado
        usuario.setAtivo(null);//validado
        Assertions.assertTrue(validacao.isCPF(usuario.getCpf()),"CPF INVALIDO");
        Assertions.assertTrue(validacao.isCNPJ(usuario.getCnpj()),"CNPJ INVALIDO");
        Assertions.assertTrue(validacao.validarTelefone(usuario.getCnpj()),"Telefone Invalido");
        Assertions.assertTrue(EmailValidator.getInstance().isValid(usuario.getEmail()));
        Assertions.assertNotNull(usuario.getLogin(),"Login Invalido");
        Assertions.assertNotNull(usuario.getSenha(),"Senha Invalido");
        Assertions.assertNotNull(usuario.getAvaliacaoPrestador(),"Avaliacao invalida");
        Assertions.assertNotNull(usuario.getAvaliacaoCliente(),"Avaliacao invalida");
        Assertions.assertNotNull(usuario.getPrestador(),"teste bolean prestador ativo");
        Assertions.assertNotNull(usuario.getAtivo(),"teste bolean ativo");
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
