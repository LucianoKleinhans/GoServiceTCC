package com.lajotasoftware.goservice.Frames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.MainActivity;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.RetrofitService;
import com.lajotasoftware.goservice.retrofit.UsuarioAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    public Long idUsuario; // usuario logado no aplicativo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_login);
        idUsuario = 0L;
        initializeComponents();
    }

    private void initializeComponents() {
        idUsuario = 0L;
        TextInputEditText inputEditTextLoginUsuario = findViewById(R.id.edtLoginUser);
        TextInputEditText inputEditTextLoginSenha = findViewById(R.id.edtLoginPass);

        MaterialButton btnEntrar = findViewById(R.id.btnEntrar);
        MaterialButton btnCadastrar = findViewById(R.id.btnCadastrar);

        RetrofitService retrofitService = new RetrofitService();
        UsuarioAPI usuarioAPI = retrofitService.getRetrofit().create(UsuarioAPI.class);

        btnEntrar.setOnClickListener(view -> {
            idUsuario = 0L;
            String loginUsuario = String.valueOf(inputEditTextLoginUsuario.getText());
            String loginSenha = String.valueOf(inputEditTextLoginSenha.getText());

            Usuario usuario = new Usuario();
            usuario.setLogin(loginUsuario);
            usuario.setSenha(loginSenha);

            if (!(usuario.getLogin().equals("")||usuario.getSenha().equals(""))) {
                usuarioAPI.authentication(usuario).enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response.body().getId() != 0) {
                            Toast.makeText(Login.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                            efetuarLogin(response.body().getId());
                        } else {
                            Toast.makeText(Login.this, "Falha no Login!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Toast.makeText(Login.this, "Falha no Login!", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(Login.this, "Login ou Senha Inválido!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void efetuarLogin(Long id) {
        idUsuario = id;
        if (idUsuario != 0L){
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
        }else{
            Toast.makeText(Login.this, "Falha no Login!", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnCadastrar (View view){
        Intent it = new Intent(this, Cadastro.class);
        startActivity(it);
    }
}
