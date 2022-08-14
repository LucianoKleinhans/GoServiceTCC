package com.lajotasoftware.goservice.Frames;

import android.content.Intent;
import android.hardware.usb.UsbInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.RetrofitService;
import com.lajotasoftware.goservice.retrofit.UsuarioAPI;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cadastro extends AppCompatActivity {

    Login login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_usuario);

        initializeComponents();
    }

    private void initializeComponents() {
        TextInputEditText inputEditTextPrimeiroNome = findViewById(R.id.edtPrimeiroNome);
        TextInputEditText inputEditTextSegundoNome = findViewById(R.id.edtSegundoNome);
        TextInputEditText inputEditTextCNPJCPF = findViewById(R.id.edtCNPJCPF);
        TextInputEditText inputEditTextTelefone = findViewById(R.id.edtTelefone);
        TextInputEditText inputEditTextEmail = findViewById(R.id.edtEmail);
        TextInputEditText inputEditTextSite = findViewById(R.id.edtSite);
        TextInputEditText inputEditTextRuaAvenida = findViewById(R.id.edtRuaAvenida);
        TextInputEditText inputEditTextNumero = findViewById(R.id.edtNumero);
        TextInputEditText inputEditTextBairro = findViewById(R.id.edtBairro);
        TextInputEditText inputEditTextCEP = findViewById(R.id.edtCEP);
        TextInputEditText inputEditTextCidade = findViewById(R.id.edtCidade);
        TextInputEditText inputEditTextUF = findViewById(R.id.edtUF);
        TextInputEditText inputEditTextLoginUsuario = findViewById(R.id.edtLoginUsuario);
        TextInputEditText inputEditTextLoginSenha = findViewById(R.id.edtLoginSenha);

        MaterialButton btn_gravar_usuario = findViewById(R.id.btnGravarCadUser);

        RetrofitService retrofitService = new RetrofitService();
        UsuarioAPI usuarioAPI = retrofitService.getRetrofit().create(UsuarioAPI.class);

        btn_gravar_usuario.setOnClickListener(view -> {
            String primeiroNome = String.valueOf(inputEditTextPrimeiroNome.getText());
            String segundoNome = String.valueOf(inputEditTextSegundoNome.getText());
            String CNPJCPF = String.valueOf(inputEditTextCNPJCPF.getText());
            String ruaAvenida = String.valueOf(inputEditTextRuaAvenida.getText());
            String bairro = String.valueOf(inputEditTextBairro.getText());
            String numero = String.valueOf(inputEditTextNumero.getText());
            String cep = String.valueOf(inputEditTextCEP.getText());
            String cidade = String.valueOf(inputEditTextCidade.getText());
            String uf = String.valueOf(inputEditTextUF.getText());
            String telefone = String.valueOf(inputEditTextTelefone.getText());
            String email = String.valueOf(inputEditTextEmail.getText());
            String site = String.valueOf(inputEditTextSite.getText());
            String loginUsuario = String.valueOf(inputEditTextLoginUsuario.getText());
            String loginSenha = String.valueOf(inputEditTextLoginSenha.getText());

            Usuario usuario = new Usuario();
            usuario.setId(login.idUsuario);
            usuario.setPrimeiroNome(primeiroNome);
            usuario.setSegundoNome(segundoNome);
            usuario.setCpf(CNPJCPF);
            usuario.setCnpj(CNPJCPF);
            usuario.setRuaAvenida(ruaAvenida);
            usuario.setBairro(bairro);
            usuario.setNumero(numero);
            usuario.setCep(cep);
            usuario.setCidade(cidade);
            usuario.setUf(uf);
            usuario.setTelefone(telefone);
            usuario.setEmail(email);
            usuario.setSite(site);
            usuario.setLogin(loginUsuario);
            usuario.setSenha(loginSenha);

            usuarioAPI.update(usuario).enqueue(new Callback<Usuario>(){
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response){
                    Toast.makeText(Cadastro.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t){
                    Toast.makeText(Cadastro.this, "Falha no salvamento!", Toast.LENGTH_SHORT).show();
                }
            });
            Intent it = new Intent(this, Login.class);
            startActivity(it);
        });
    }

 /*   public void btn_gravar_usuario (View view){
        Usuario usuario = new Usuario();
        usuario.setNome(nomeCad.getText().toString());
        usuario.setCpf(cpfcnpjCad.getText().toString());
        usuario.setEndereco(enderecoCad.getText().toString());
        usuario.setTelefone(telefoneCad.getText().toString());
        usuario.setEmail(emailCad.getText().toString());
        usuario.setLogin(loginCad.getText().toString());
        usuario.setSenha(senhaCad.getText().toString());

    }*/
}
