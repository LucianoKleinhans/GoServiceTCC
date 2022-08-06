package com.lajotasoftware.goservice.Frames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.R;

public class Cadastro extends AppCompatActivity {

    private Usuario usuario;

    private EditText nomeCad;
    private EditText cpfcnpjCad;
    private EditText enderecoCad;
    private EditText telefoneCad;
    private EditText emailCad;
    private EditText loginCad;
    private EditText senhaCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastroUsuario);

        Intent i = getIntent();
        nomeCad = findViewById(R.id.edtPrimeiroNome);
        cpfcnpjCad = findViewById(R.id.edtCNPJCPF);
        enderecoCad = findViewById(R.id.edtCidade);
        telefoneCad = findViewById(R.id.edtTelefone);
        emailCad = findViewById(R.id.edtEmail);
        loginCad = findViewById(R.id.edtUsuarioProv);
        senhaCad = findViewById(R.id.edtSenhaProv);
    }

    public void btn_gravar_usuario (View view){
        usuario.setNome(nomeCad.getText().toString());
        usuario.setCpf(cpfcnpjCad.getText().toString());
        usuario.setEndereco(enderecoCad.getText().toString());
        usuario.setTelefone(telefoneCad.getText().toString());
        usuario.setEmail(emailCad.getText().toString());
        usuario.setLogin(loginCad.getText().toString());
        usuario.setSenha(senhaCad.getText().toString());
    }
}
