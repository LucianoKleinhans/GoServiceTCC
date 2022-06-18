package testes;

import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.Usuario;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

public class ServicoUC {

    @Test
    public void CriaServico() {//test unitario
        Servico servico = new Servico();
        Usuario usuario = new Usuario();

        servico.setId(1L);
        servico.setNome("Formatação com backup");

        //Categoria sera tratada diretamente no front, Não será possivel selecionar valores invalidos.
        servico.setCategoria("Manutenção");
        servico.setSubCategoria("Informática");

        servico.setValor(125.00);
        servico.setObsServico("Faço formatação com backup em qualquer dia da semana");
        //FK
        usuario.setId_Prestador("123adb");
        servico.setId_Prestador(usuario);//id_Prestador
        servico.getId_Prestador().getId_Prestador();

        Assertions.assertNotNull(servico.getId(), "ID incorreto");
        Assertions.assertNotNull(servico.getNome(), "Nome incorreto");
        Assertions.assertNotNull(servico.getCategoria(), "Categoria incorreta");
        Assertions.assertNotNull(servico.getSubCategoria(), "Sub Categoria incorreta");
        Assertions.assertNotNull(servico.getValor(), "Valor incorreto");
        Assertions.assertNotNull(servico.getObsServico(), "Obs incorreta");
        Assertions.assertNotNull(servico.getId_Prestador(), "Id Prestador Incorreto");
    }
    @Test
    public void naoCriaServico() {//test unitario
        Servico servico = new Servico();
        Usuario usuario = new Usuario();

        servico.setId(1L);
        servico.setNome("Formatação com backup");
        servico.setCategoria("Manutenção");
        servico.setSubCategoria("Informática");
        servico.setValor(125.00);
        servico.setObsServico("Faço formatação com backup em qualquer dia da semana");
        //FK
        usuario.setId_Prestador("123adb");
        servico.setId_Prestador(usuario);//id_Prestador
        servico.getId_Prestador().getId_Prestador();

        Assertions.assertNotNull(servico.getId(), "ID incorreto");
        Assertions.assertNotNull(servico.getNome(), "Nome incorreto");
        Assertions.assertNotNull(servico.getCategoria(), "Categoria incorreta");
        Assertions.assertNotNull(servico.getSubCategoria(), "Sub Categoria incorreta");
        Assertions.assertNotNull(servico.getValor(), "Valor incorreto");
        Assertions.assertNotNull(servico.getObsServico(), "Obs incorreta");
        Assertions.assertNotNull(servico.getId_Prestador().getId_Prestador(), "Id Prestador Incorreto");
    }

}
