package com.lajotasoftware.goservice;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.Frames.Login;
import com.lajotasoftware.goservice.R;

import android.view.View;
import android.widget.Toast;

import com.lajotasoftware.goservice.Frames.Perfil;
import com.lajotasoftware.goservice.retrofit.RetrofitService;
import com.lajotasoftware.goservice.retrofit.UsuarioAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    long idUsuario;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        Bundle parametros = it.getExtras();
        idUsuario = parametros.getLong("id_usuario");
        //username = parametros.getString("username");
        MaterialTextView textViewNomeUsuario = findViewById(R.id.ttvUsernamePerfilUser);
        //textViewNomeUsuario.setText(username);
        setContentView(R.layout.activity_main);
        initializeComponents();
    }

    private void initializeComponents() {

    }

    public void btn_main_to_perfil (View view){
        Intent it = new Intent(this, Perfil.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        it.putExtras(parametros);
        startActivity(it);
    }
}