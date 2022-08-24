package com.lajotasoftware.goservice.Frames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
    public String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RetrofitService retrofitService = new RetrofitService();
        UsuarioAPI usuarioAPI = retrofitService.getRetrofit().create(UsuarioAPI.class);
        usuarioAPI.testConnection().enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                setContentView(R.layout.entry_login);
                idUsuario = 0L;
                initializeComponents();
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                setContentView(R.layout.without_connection);
                ImageView btnRefresh = findViewById(R.id.btnRefresh);
            }
        });

    }

    private void initializeComponents() {
        idUsuario = 0L;
        TextInputEditText inputEditTextLoginUsuario = findViewById(R.id.edtCadLoginUser);
        TextInputEditText inputEditTextLoginSenha = findViewById(R.id.edtCadLoginPass);

        MaterialButton btnEntrar = findViewById(R.id.btnCadLoginGravar);
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
            if (usuario.getLogin().length()>=5){
                if (usuario.getSenha().length()>=10){
                    if (!(usuario.getLogin().equals("") || usuario.getSenha().equals(""))) {
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
                }else{
                    Toast.makeText(Login.this, "Tamanho da Senha do Usuário deve ser maior ou igual a 10", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(Login.this, "Tamanho do Nome de Usuário deve ser maior ou igual a 5", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void efetuarLogin(Long id) {
        idUsuario = id;
        if (idUsuario != 0L){
            Intent it = new Intent(this, MainActivity.class);
            Bundle parametros = new Bundle();
            parametros.putLong("id_usuario", idUsuario);
            it.putExtras(parametros);
            startActivity(it);
        }else{
            Toast.makeText(Login.this, "Falha no Login!", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnCadastrar (View view){
        Intent it = new Intent(this, Cadastro.class);
        Bundle parametros = new Bundle();
        String status = "CADASTRAR_USUARIO";
        parametros.putLong("id_usuario", idUsuario);
        parametros.putString("status_usuario", status);
        it.putExtras(parametros);
        startActivity(it);
    }

    public void refresh(View view) {
        RetrofitService retrofitService = new RetrofitService();
        UsuarioAPI usuarioAPI = retrofitService.getRetrofit().create(UsuarioAPI.class);
        usuarioAPI.testConnection().enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                setContentView(R.layout.entry_login);
                idUsuario = 0L;
                initializeComponents();
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }
}
