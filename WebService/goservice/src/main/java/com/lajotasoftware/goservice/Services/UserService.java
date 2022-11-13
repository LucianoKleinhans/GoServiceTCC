package com.lajotasoftware.goservice.Services;

import com.lajotasoftware.goservice.DAO.*;
import com.lajotasoftware.goservice.Entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class UserService {
    @Autowired
    DAOUsuario daoUsuario;
    @Autowired
    DAOProposta daoProposta;
    @Autowired
    DAOSolicitaServico daoSolicitaServico;
    @Autowired
    DAOPedido daoPedido;
    @Autowired
    DAOServico daoServico;

    @Autowired
    DAOCategoria daoCategoria;

    @Autowired
    DAOSubCategoria daoSubCategoria;

    private String senha, senhaBanco, senhaBancoRecuperacao, login, novaSenha, codConfirmacao;
    private Usuario user = new Usuario();
    private Return ret = new Return();

    private final EmailService emailService;

    public UserService(EmailService emailService) {
        this.emailService = emailService;
    }

    private BCryptPasswordEncoder passwordEnconder() {
        return new BCryptPasswordEncoder();
    }

    public Return existeUser(String login, String email) {
        Return ret = new Return();
        ret.setStatusCode(200);
        ret.setStatus("Login e Senha Válido!");
        ret.setText("Login de usuário e senha são válidos!");
        if (daoUsuario.findByLogin(login) != null) {
            ret.setStatusCode(500);
            ret.setStatus("Login Inválido!");
            ret.setText("Login de usuário já existente!");
        } else if (daoUsuario.findByEmail(email) != null){
            ret.setStatusCode(500);
            ret.setStatus("E-mail Inválido!");
            ret.setText("E-mail de usuário já existente!");
        }
        return ret;
    }

    public Usuario execute (Usuario usuario){
        usuario.setSenha(passwordEnconder().encode(usuario.getSenha()));
        usuario.setPrestador(false);
        usuario.setAtivo(false);
        usuario.setAvaliacaoCliente(0.0);
        usuario.setAvaliacaoPrestador(0.0);
        usuario.setMaster(false);
        daoUsuario.save(usuario);

        emailService.sendEmail(
                usuario.getEmail(),
                "GoService - Bem vindo",
                "Olá, "+usuario.getLogin()+"\n Seja Bem Vindo!");

        return usuario;
    }

    public Usuario validation(Usuario usuario) {
        senha = usuario.getSenha();
        login = usuario.getLogin();
        Usuario existUser = null;
        if (daoUsuario.findByLogin(usuario.getLogin()) != null){
            existUser = daoUsuario.findByLogin(usuario.getLogin());
        }if(daoUsuario.findByEmail(usuario.getLogin()) != null){
            existUser = daoUsuario.findByEmail(usuario.getLogin());
        }
        if (existUser != null && senha != null) {
            senhaBanco = existUser.getSenha();
            senhaBancoRecuperacao = existUser.getSenhaRecuperacao();
            boolean valid = false;
            valid = BCrypt.checkpw(senha, senhaBanco);
            if (valid) {
                return existUser;
            } else {
                if (senhaBancoRecuperacao != null){
                    valid = BCrypt.checkpw(senha, senhaBancoRecuperacao);
                    if (valid){
                        return existUser;
                    } else {
                        user.setId(0L);
                    }
                }else {
                    user.setId(0L);
                }
            }
        }else {
            user.setId(0L);
        }
        return user;
    }

    public Usuario getAtualUser (Usuario id){
        Usuario userAtual = daoUsuario.findById(id.getId()).get();
        return userAtual;
    }

    public Boolean getConnection (){
        return true;
    }

    public Boolean esqueceSenha(String email) {
        Usuario user = daoUsuario.findByEmail(email);
        if (user!=null) {
            try {
                //random password generation
                novaSenha = passwordGeneration();
                //envio de email
                emailService.sendEmail(
                        email,
                        "GoService - Recuperação de senha",
                        "Olá, " + user.getPrimeiroNome() + "\n Essa será sua nova senha para acessar o aplicativo GoService \n Senha: " + novaSenha +
                                "\n\n *Atenção, recomendamos que para a segurança de sua conta essa senha seja alterada para uma senha de sua escolha dentro do aplicativo.\n" +
                                "Para alterar a sua senha deve ir em Perfil > Editar Perfil > Alterar Senha." +
                                "\n **Obs. A senha deve conter pelo menos 10 caracteres!");
                //update de senha na tabela de usuario
                daoUsuario.senhaRecuperacao(user.getId(), passwordEnconder().encode(novaSenha));
                //return
                return true;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            return false;
        }
    }

    public Boolean alterarSenha(Long id, String senha) {
        Usuario user = daoUsuario.getUsuario(id);
        try {
            daoUsuario.alterarSenha(user.getId(), passwordEnconder().encode(senha));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //random password generation function
    public String passwordGeneration() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public String codEmailGeneration() {
        int leftLimit = 48; // number '0'
        int rightLimit = 57; // number '9'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    public Return codConfirmacaoEmail(String email) {
        try {
            codConfirmacao = codEmailGeneration();
            emailService.sendEmail(
                    email,
                    "GoService - Codigo de Confirmação",
                    "Olá,\nO seu código de confirmação é: "+codConfirmacao);
            ret.setStatusCode(200);
            ret.setStatus("Sucesso");
            ret.setText(codConfirmacao);
            return ret;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //-------------------- functions user --------------------//

    //Calcula avaliacao usuario

    public void calc_avaliacao_prestador(){
        try {
            List<Usuario> usuarios = new ArrayList<>();
            usuarios = daoUsuario.findAll();
            for (int i = 0; i < usuarios.size(); i++) {
                Long idUser = usuarios.get(i).getId();
                Integer qtdPedidos = daoUsuario.contPedidosFinalizadosPrestador(idUser);
                if (qtdPedidos > 0) {
                    Double avaliacao = daoUsuario.contAvaliacaoPrestador(idUser, qtdPedidos);
                    if (avaliacao != null) {
                        daoUsuario.updateAvaliacaoPrestador(idUser, avaliacao);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void calc_avaliacao_cliente(){
        try {
            List<Usuario> usuarios = new ArrayList<>();
            usuarios = daoUsuario.findAll();
            for (int i = 0; i < usuarios.size(); i++) {
                Long idUser = usuarios.get(i).getId();
                Integer qtdPedidos = daoUsuario.contPedidosFinalizadosCliente(idUser);
                if (qtdPedidos > 0){
                    Double avaliacao = daoUsuario.contAvaliacaoCliente(idUser, qtdPedidos);
                    if (avaliacao != null) {
                        daoUsuario.updateAvaliacaoCliente(idUser, avaliacao);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setValorProposto(Long id, Double valor) {
        Double menorValor = daoSolicitaServico.getMenorValorProposto(id);
        if (valor == null){
            valor = daoSolicitaServico.getById(id).getValor();
        }
        if (menorValor!=null) {
            if (menorValor > valor) {
                daoSolicitaServico.setMenorvalorProposto(id, valor);
            } else {
                daoSolicitaServico.setMenorvalorProposto(id, menorValor);
            }
        }else{
            daoSolicitaServico.setMenorvalorProposto(id, valor);
        }
    }

    public Return getPropostaFeita(Long idPrestador, Long idSolicitacao) {
        Proposta proposta = daoProposta.getPropostaJaFeita(idPrestador,idSolicitacao);
        Return ret = new Return();
        if (proposta != null){
            ret.setStatusCode(200);
            ret.setStatus("PROPOSTA JA CRIADA");
            ret.setText("Já possui uma proposta sua feita para essa solicitção!");
        }else {
            ret.setStatusCode(100);
            ret.setStatus("PROPOSTA NAO CRIADA");
            ret.setText("Não possui nenhuma proposta sua feita para essa solicitção!");
        }
        return ret;
    }

    public void notificaCliente(Proposta proposta) {
        try {
            Proposta prop = daoProposta.getById(proposta.getId());
            Usuario cliente = daoUsuario.getById(proposta.getId_Cliente().getId());
            Usuario prestador = daoUsuario.getById(proposta.getId_Prestador().getId());

            emailService.sendEmail(
                    cliente.getEmail(),
                    "GoService - Nova Proposta Recebida!",
                    "Olá, " + cliente.getPrimeiroNome() + "\n Você recebeu uma nova proposta de " + prestador.getLogin() +
                            "Para a solicitação de nome: " + prop.getId_SolicitaServico().getNomeServico() + "\n\nDetalhes da proposta" +
                            "\n - Usuario : " + prestador.getLogin() +
                            "\n - Proposta : " + prop.getObservacao() +
                            "\n - Valor Proposto : " + prop.getValor());
        } catch (Exception e){
            System.out.println("Erro ao enviar e-mail");
        }
    }

    public Return setStatusProposta(Long idProposta, String status) {
        Return ret = new Return();
        Proposta proposta = daoProposta.getPropostaById(idProposta);
        if (status.equals("ACEITA")){
            daoProposta.setStatusProposta(idProposta, status);
            ret.setStatusCode(200);
            ret.setStatus("PROPOSTA ACEITA");
            ret.setText("Proposta Aceita!");
            Pedido pedido = new Pedido();
            pedido.setId_Cliente(proposta.getId_Cliente());
            pedido.setId_Prestador(proposta.getId_Prestador());
            pedido.setId_ServicoSolicitado(proposta.getId_SolicitaServico());
            pedido.setServicoSolicitado(true);
            pedido.setStatus("ACEITO");
            pedido.setId_Proposta(proposta);
            daoPedido.save(pedido);
        }else if (status.equals("RECUSADA")) {
            daoProposta.setStatusProposta(idProposta, status);
            ret.setStatusCode(200);
            ret.setStatus("PROPOSTA RECUSADA");
            ret.setText("Proposta Recusada!");
        }
        return ret;
    }

    public void notificaPedido (Pedido pedido){
        Usuario cliente = daoUsuario.getById(pedido.getId_Cliente().getId());
        Usuario prestador = daoUsuario.getById(pedido.getId_Prestador().getId());

        try {
            String emailCliente = pedido.getId_Prestador().getEmail();
            if (pedido.getServicoSolicitado()){
                Proposta proposta = daoProposta.getPropostaById(pedido.getId_Proposta().getId());
                emailService.sendEmail(
                        emailCliente,
                        "GoService - Novo Pedido!",
                        "Olá, " + prestador.getPrimeiroNome() + "\n A proposta feita para a solicitação de serviço de " + cliente.getPrimeiroNome() + "foi aceita"+
                                "\n\nDetalhes da proposta" +
                                "\n - Usuario : " + cliente.getPrimeiroNome() +
                                "\n - Proposta : " + proposta.getObservacao() +
                                "\n - Valor Proposto : " + proposta.getValor());
            }else{
                Servico servico = daoServico.getServicoByID(pedido.getId_Servico().getId());
                emailService.sendEmail(
                        emailCliente,
                        "GoService - Novo Pedido!",
                        "Olá, " + prestador.getPrimeiroNome() + "\n Existe um novo pedido de seu serviço! " +
                                "\n\nDetalhes do Pedido" +
                                "\n - Usuario : " + cliente.getPrimeiroNome() +
                                "\n - Serviço Solicitado : " + servico.getNome() +
                                "\n - Valor do Serviço : " + servico.getValor());
            }
        } catch (Exception e){
            System.out.println("Erro ao enviar e-mail");
        }
    }

    public Return pedidoVerificaSeExiste(Long idCliente, Long idServico) {
        Return ret = new Return();
        List<Pedido> pedidos = daoPedido.verificaSeExiste(idCliente, idServico);
        if (pedidos.size()>0){
            ret.setStatusCode(200);
            ret.setStatus("PEDIDO JA CRIADA");
            ret.setText("Já possui um pedido seu para esse serviço!");
        }else {
            ret.setStatusCode(100);
            ret.setStatus("PEDIDO NAO CRIADO");
            ret.setText("Não possui nenhuma pedido seu para esse serviço!");
        }
        return ret;
    }

    public List<Servico> getServicoByCategoria(Long idCategoria, Long idSubCategoria) {
        List<Servico> servicos;
        Categoria categoria = daoCategoria.getById(idCategoria);
        SubCategoria subCategoria = daoSubCategoria.getById(idSubCategoria);
        if (subCategoria.getNome().equals("Geral")){
            servicos = daoServico.getServicosCategoria2(idCategoria);
        } else {
            servicos =  daoServico.getServicosCategoria1(idCategoria, idSubCategoria);
        }
        return servicos;

    }
}
