package com.lajotasoftware.goservice.Services;

import com.lajotasoftware.goservice.DAO.DAOUsuario;
import com.lajotasoftware.goservice.Entity.Return;
import com.lajotasoftware.goservice.Entity.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private String senha, senhaBanco, login, novaSenha, codConfirmacao;
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
            boolean valid = false;
            valid = BCrypt.checkpw(senha, senhaBanco);
            if (valid) {
                return existUser;
                //user.setId(existUser.getId());
            } else {
                user.setId(0L);
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
                daoUsuario.alteraSenha(user.getId(), passwordEnconder().encode(novaSenha));
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
            daoUsuario.alteraSenha(user.getId(), passwordEnconder().encode(novaSenha));
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
}
