package com.lajotasoftware.goservice.Services;

import com.lajotasoftware.goservice.DAO.DAOUsuario;
import com.lajotasoftware.goservice.Entity.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class UserService {
    @Autowired
    DAOUsuario daoUsuario;
    private String senha, senhaBanco, login, novaSenha;
    private Usuario user = new Usuario();

    private final EmailService emailService;

    public UserService(EmailService emailService) {
        this.emailService = emailService;
    }

    private BCryptPasswordEncoder passwordEnconder() {
        return new BCryptPasswordEncoder();
    }

    public Usuario execute (Usuario usuario){

        Usuario existUser = daoUsuario.findByLogin(usuario.getLogin());
        if (existUser != null) {
                throw new Error("Usuario inválido!");
        }
        existUser = daoUsuario.findByEmail(usuario.getEmail());
        if (existUser != null) {
            throw new Error("Email inválido!");
        }
        usuario.setSenha(passwordEnconder().encode(usuario.getSenha()));
        usuario.setPrestador(false);
        usuario.setAtivo(false);
        Usuario createdUser = daoUsuario.save(usuario);

        return createdUser;
    }

    public Usuario validation(Usuario usuario) {
        senha = usuario.getSenha();
        login = usuario.getLogin();
        Usuario existUser = daoUsuario.findByLogin(usuario.getLogin());
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

    public String redefinirSenha(Long id, String email) {
        Usuario user = daoUsuario.getUsuario(id);

        if (user.getEmail().toString().equals(email)){
            try {
                //random password generation
                novaSenha = passwordGeneraion();
                //envio de email
                emailService.sendEmail(
                        email,
                        "GoService - Recuperação de senha",
                        "Olá, "+user.getPrimeiroNome()+"\n Essa será sua nova senha para acessar o aplicativo GoService \n Senha: "+novaSenha+
                                "\n\n *Atenção, recomendamos que para a segurança de sua conta essa senha seja alterada para uma senha de sua escolha dentro do aplicativo.\n" +
                                "Para alterar a sua senha deve ir em Perfil > Editar Perfil > Alterar Senha." +
                                "\n **Obs. A senha deve conter pelo menos 10 caracteres!");
                //update de senha na tabela de usuario
                daoUsuario.alteraSenha(id, passwordEnconder().encode(novaSenha));
                //return
                return "Nova senha enviada para o email: " + email;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            return "Email incorreto!";
        }
    }

    //random password generation function
    public String passwordGeneraion() {
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
}
