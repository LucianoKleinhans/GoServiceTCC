package com.lajotasoftware.goservice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.Frames.Card;
import com.lajotasoftware.goservice.Frames.Pedidos;
import com.lajotasoftware.goservice.Frames.Prestadores;

import android.view.View;

import com.lajotasoftware.goservice.Frames.Perfil;
import com.lajotasoftware.goservice.retrofit.RetrofitService;
import com.lajotasoftware.goservice.retrofit.API;

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
        setContentView(R.layout.activity_main);
        initializeComponents();
    }

    private void initializeComponents() {
        MaterialTextView textViewUserName = findViewById(R.id.ttvUsernamePerfilUser2);

        RetrofitService retrofitService = new RetrofitService();
        API usuarioAPI = retrofitService.getRetrofit().create(API.class);
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        usuarioAPI.getAtualUser(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> usuarioResponse) {
                if (usuarioResponse.isSuccessful()) {
                    Usuario user = new Usuario();
                    assert usuarioResponse.body() != null;
                    user.setUsuario(usuarioResponse.body());

                    username = user.getLogin();
                    textViewUserName.setText(username);
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                throw new Error("USUARIO INVALIDO");
            }
        });
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Atenção");
        dialog.setMessage("Tem certeza que deseja sair?");
        dialog.setNegativeButton("Não", null);
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        }).create();
        dialog.show();
    }

    public void btn_main_to_perfil (View view){
        Intent it = new Intent(this, Perfil.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        it.putExtras(parametros);
        startActivity(it);
    }

    public void btn_main_to_card (View view) {
        Intent it = new Intent(this, Card.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        parametros.putString("status_usuario", "DEFAUT");
        it.putExtras(parametros);
        startActivity(it);
    }


    public void btn_main_to_prestadores(View view) {
        Intent it = new Intent(this, Prestadores.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        parametros.putString("status", "DEFAUT");
        it.putExtras(parametros);
        startActivity(it);
    }

    public void btn_main_to_solicitacoes(View view) {
        Intent it = new Intent(this, Pedidos.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);;
        it.putExtras(parametros);
        startActivity(it);
    }
}