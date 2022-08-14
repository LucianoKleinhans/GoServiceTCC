package com.lajotasoftware.goservice.Services;

import antlr.BaseAST;
import com.lajotasoftware.goservice.DAO.DAOUsuario;
import com.lajotasoftware.goservice.Entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CreateUserService {
    @Autowired
    DAOUsuario daoUsuario;
    private String senha, senhaBanco, login;
    private Usuario user = new Usuario();
    private BCryptPasswordEncoder passwordEnconder() {
        return new BCryptPasswordEncoder();
    }

    public Usuario execute (Usuario usuario){

        Usuario existUser = daoUsuario.findByLogin(usuario.getLogin());
        if (existUser != null) {
                throw new Error("Usuario inválido!");
        }
        usuario.setSenha(passwordEnconder().encode(usuario.getSenha()));
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
                user.setId(existUser.getId());
            } else {
                user.setId(0L);
            }
        }else {
            user.setId(0L);
        }
        return user;
    }
}
