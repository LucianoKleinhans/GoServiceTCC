package com.lajotasoftware.goservice.Frames;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.Functions.Function;
import com.lajotasoftware.goservice.MainActivity;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.RetrofitService;
import com.lajotasoftware.goservice.retrofit.API;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    public Long idUsuario; // usuario logado no aplicativo
    public String username;
    public Function function = new Function();
    Dialog dialog;

    ProgressBar progressBarLogin;
    ProgressBar progressBarEsqueciSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RetrofitService retrofitService = new RetrofitService();
        API usuarioAPI = retrofitService.getRetrofit().create(API.class);
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
        dialog = new Dialog(this);
        idUsuario = 0L;
        TextInputEditText inputEditTextLoginUsuario = findViewById(R.id.edtCadLoginUser);
        TextInputEditText inputEditTextLoginSenha = findViewById(R.id.edtCadLoginPass);

        MaterialButton btnEntrar = findViewById(R.id.btnCadLoginGravar);
        MaterialButton btnCadastrar = findViewById(R.id.btnCadastrar);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        progressBarLogin.setVisibility(View.GONE);
        btnEntrar.setEnabled(true);
        btnCadastrar.setEnabled(true);

        RetrofitService retrofitService = new RetrofitService();
        API usuarioAPI = retrofitService.getRetrofit().create(API.class);

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
                        progressBarLogin.setVisibility(View.VISIBLE);
                        btnEntrar.setEnabled(false);
                        btnCadastrar.setEnabled(false);
                        usuarioAPI.authentication(usuario).enqueue(new Callback<Usuario>() {
                            @Override
                            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                if (response.body().getId() != 0) {
                                    btnEntrar.setEnabled(true);
                                    btnCadastrar.setEnabled(true);
                                    Toast.makeText(Login.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                    efetuarLogin(response.body().getId());
                                } else {
                                    Toast.makeText(Login.this, "Falha no Login!", Toast.LENGTH_SHORT).show();
                                    progressBarLogin.setVisibility(View.GONE);
                                    btnEntrar.setEnabled(true);
                                    btnCadastrar.setEnabled(true);
                                }
                            }

                            @Override
                            public void onFailure(Call<Usuario> call, Throwable t) {
                                Toast.makeText(Login.this, "Falha no Login!", Toast.LENGTH_SHORT).show();
                                progressBarLogin.setVisibility(View.GONE);
                                btnEntrar.setEnabled(true);
                                btnCadastrar.setEnabled(true);
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
            progressBarLogin.setVisibility(View.GONE);
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
        API usuarioAPI = retrofitService.getRetrofit().create(API.class);
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

    public void esqueciMinhaSenha(View view) {
        dialog.setContentView(R.layout.z_custom_alertdialog_esqueci_senha);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextInputEditText edtEmailEsqueciSenha = dialog.findViewById(R.id.edtEmailEsqueciSenha);
        MaterialButton btnConfirmaEsqueciSenha = dialog.findViewById(R.id.btnConfirmaEsqueciSenha);
        progressBarEsqueciSenha = dialog.findViewById(R.id.progressBarEsqueciSenha);
        progressBarEsqueciSenha.setVisibility(View.GONE);
        edtEmailEsqueciSenha.setEnabled(true);
        btnConfirmaEsqueciSenha.setEnabled(true);

        RetrofitService retrofitService = new RetrofitService();
        API api = retrofitService.getRetrofit().create(API.class);

        btnConfirmaEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailEsqueciSenha = String.valueOf(edtEmailEsqueciSenha.getText());
                if (emailEsqueciSenha!=null) {
                    progressBarEsqueciSenha.setVisibility(View.VISIBLE);
                    edtEmailEsqueciSenha.setEnabled(false);
                    btnConfirmaEsqueciSenha.setEnabled(false);
                    api.forgetPassword(emailEsqueciSenha).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.body().equals(false)) {
                                Toast.makeText(Login.this, "Email inválido!", Toast.LENGTH_SHORT).show();
                                progressBarEsqueciSenha.setVisibility(View.GONE);
                                edtEmailEsqueciSenha.setEnabled(true);
                                btnConfirmaEsqueciSenha.setEnabled(true);
                            } else {
                                progressBarEsqueciSenha.setVisibility(View.GONE);
                                Toast.makeText(Login.this, "Senha de recuperação enviada para o e-mail \n" + emailEsqueciSenha, Toast.LENGTH_LONG).show();
                                dialog.hide();
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });
                }
            }
        });
        dialog.show();
    }
}
