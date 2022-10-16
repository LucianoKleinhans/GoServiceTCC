package testes;

import com.lajotasoftware.goservice.Controller.UsuarioController;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.GoserviceApplication;
import com.lajotasoftware.goservice.Services.Functions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = GoserviceApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioUC {

    Functions validacao = new Functions();

    @Test
    public void criaUsuario(){//test unitario
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setId_Prestador(1L);
       // usuario.setNome("Bruno Formiga");
        usuario.setCpf("04902773147");
        usuario.setCnpj("11162692000190");
     //   usuario.setEndereco("Bom dia ao lado do boa noite");
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
        usuario.setId_Prestador(1L);
       // usuario.setNome("Ricardo Milos");
        usuario.setCpf("000.000.000-00");
        usuario.setCnpj("00.000.000/0000-00");
       /// usuario.setEndereco("Bom dia ao lado do boa noite");
        usuario.setTelefone("67 67894-5465");
        usuario.setEmail("eobrunoformigagmail.com");
        usuario.setLogin("formigao");
        usuario.setSenha("bee");
        usuario.setAvaliacaoPrestador(9.0);//Sem necessidade de testar pois vai ser feito o controle no front
        usuario.setAvaliacaoCliente(500.00);//Sem necessidade de testar pois vai ser feito o controle no front
        usuario.setPrestador(null);
        usuario.setAtivo(null);

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

    /*TESTE INTEGRAÇÃO*/
    @Autowired
    public UsuarioController usuarioController;
    @Test
    public void casoUsoSalva() {//teste integracao
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setId_Prestador(1L);
        //usuario.setNome("Bruno Formiga");
        usuario.setCpf("07464993101");
        usuario.setCnpj("11162692000190");
        //usuario.setEndereco("Bom dia ao lado do boa noite");
        usuario.setTelefone("67996002604");
        usuario.setEmail("eobrunoformiga@gmail.com");
        usuario.setLogin("formigabruno");
        usuario.setSenha("beemovieee");
        usuario.setAvaliacaoPrestador(5.0);
        usuario.setAvaliacaoCliente(4.5);
        usuario.setPrestador(true);
        usuario.setAtivo(true);

        //usuarioController.salvarUsuario(usuario);
    }

    @Test
    public void casoUsoTornaPrestador() {//teste integracao
    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setId_Prestador(1L);
    usuario.setPrestador(false);

    usuarioController.update(1l,usuario);
    }

}
