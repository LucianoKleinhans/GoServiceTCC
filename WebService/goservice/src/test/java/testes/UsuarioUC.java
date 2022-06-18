package testes;

import com.lajotasoftware.goservice.DAO.DAOUsuario;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.sources.Validacao;
import org.apache.commons.validator.routines.EmailValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioUC {

    Validacao validacao = new Validacao();

    @Test
    public void criaUsuario(){//test unitario
        Usuario usuario = new Usuario();
        usuario.setId(99L);
        usuario.setId_Prestador("123abc");
        usuario.setNome("Bruno Formiga");
        usuario.setCpf("04902773147");
        usuario.setCnpj("11162692000190");
        usuario.setEndereco("Bom dia ao lado do boa noite");
        usuario.setTelefone("67996002604");
        usuario.setEmail("eobrunoformiga@gmail.com");
        usuario.setLogin("formigabruno");
        usuario.setSenha("beemovieee");
        usuario.setAvaliacaoPrestador(5.0);//Sem necessidade de testar pois vai ser feito o controle no front
        usuario.setAvaliacaoCliente(4.5);//Sem necessidade de testar pois vai ser feito o controle no front
        usuario.setPrestador(true);
        usuario.setAtivo(true);

        Assertions.assertNotNull(usuario.getCpf(),"CPF INVALIDO");//
        Assertions.assertNotNull(usuario.getCnpj(),"CNPJ INVALIDO");
        Assertions.assertNotNull(usuario.getTelefone(),"Telefone Invalido");
        Assertions.assertNotNull(usuario.getEmail(),"Email Invalido");
        Assertions.assertNotNull(usuario.getLogin(),"Login Invalido");
        Assertions.assertNotNull(usuario.getSenha(),"Senha Invalido");
        Assertions.assertNotNull(usuario.getAvaliacaoPrestador(),"Avaliacao invalida");
        Assertions.assertNotNull(usuario.getAvaliacaoCliente(),"Avaliacao invalida");
        Assertions.assertNotNull(usuario.getPrestador(),"teste bolean prestador ativo");
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

    /*TESTE INTEGRAÇÃO*/

    @Autowired
    private DAOUsuario repository;
    @Test
    public void casoUsoSalvarComExito(){//teste integracao
        repository
        Usuario usuario = new Usuario();

        URL url = new URL ("http://localhost:9090/usuario/");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = "\"{\"\"id\"\":1,\"id_Prestador\":\"123abc\",\"nome\":\"Bruno Formiga\",\"cpf\":\"07464993101\",\"cnpj\":\"11162692000190\",\"endereco\":\"Bom dia ao lado do boa noite\",\"telefone\":\"67996002604\",\"email\":\"eobrunoformiga@gmail.com\",\"login\":\"formigabruno\",\"senha\":\"beemovieee\",\"avaliacaoPrestador\":5.0,\"avaliacaoCliente\":4.5,\"prestador\":true,\"ativo\":true}\"";
    }
    public salvarUsuario (@RequestBody String usuario) {
        return repository.save(usuario);
    }

    @Test
    public void casoUsoTornaPrestador() throws IOException {//teste integracao
        URL url = new URL ("http://localhost:9090/usuario/1");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = "\"{\"\"id_Prestador\":\"123abc\"prestador\":true\"}\"";
    }

}
