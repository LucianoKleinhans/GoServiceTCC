package com.lajotasoftware.goservice.Frames;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.MainActivity;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.RetrofitService;
import com.lajotasoftware.goservice.retrofit.UsuarioAPI;

import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil extends AppCompatActivity {

    private Long idUsuario;
    Usuario usuario = new Usuario();
    private Boolean prestador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent it = getIntent();
        Bundle parametros = it.getExtras();
        idUsuario = parametros.getLong("id_usuario");
        setContentView(R.layout.perfil_usuario);
        initializeComponents();
    }

    private void initializeComponents() {
        MaterialTextView textViewNomeUsuario = findViewById(R.id.ttvUsernamePerfilUser);
        MaterialTextView textViewCidadeUsuario = findViewById(R.id.ttvCidadePerfilUser);
        MaterialTextView textViewEmailUsuario = findViewById(R.id.ttvEmailPerfilUser);
        MaterialTextView textViewSiteUsuario = findViewById(R.id.ttvSitePerfilUser);

        MaterialButton btnTornarUserPrestador = findViewById(R.id.btnTornarPresPerfilUser);

        RetrofitService retrofitService = new RetrofitService();
        UsuarioAPI usuarioAPI = retrofitService.getRetrofit().create(UsuarioAPI.class);
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        usuarioAPI.getAtualUser(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> usuarioResponse) {
                if (usuarioResponse.isSuccessful()) {
                    Usuario user = new Usuario();

                    assert usuarioResponse.body() != null;
                    user.setUsuario(usuarioResponse.body());

                    /*Deixar botao prestador visivel*/
                    if (user.getPrestador()){
                        btnTornarUserPrestador.setVisibility(View.INVISIBLE);
                    }else{
                        btnTornarUserPrestador.setVisibility(View.VISIBLE);
                    }

                    textViewNomeUsuario.setText(user.getPrimeiroNome());
                    textViewCidadeUsuario.setText("Cidade:" + user.getCidade() + " - " + user.getUf());
                    textViewEmailUsuario.setText("E-mail:" + user.getEmail());
                    if(user.getSite() == null){
                        textViewSiteUsuario.setVisibility(View.INVISIBLE);
                    }else{textViewSiteUsuario.setText("Site:" + user.getSite());}
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                throw new Error("USUARIO INVALIDO");
            }
        });

        btnTornarUserPrestador.setOnClickListener(view -> {
            AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Tornar Usuario Prestador");
            alertDialogBuilder
                .setMessage("Clique sim para se tornar Prestador!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            RetrofitService retrofitService = new RetrofitService();
                            UsuarioAPI usuarioAPI = retrofitService.getRetrofit().create(UsuarioAPI.class);

                                Usuario usuario = new Usuario();

                                usuario.setId(idUsuario);
                                usuario.setId_Prestador(idUsuario+100);
                                usuario.setPrestador(true);
                                usuarioAPI.update(idUsuario, usuario).enqueue(new Callback<Usuario>() {
                                @Override
                                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                    if (response.code() == 200) {
                                        Toast.makeText(Perfil.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
                                        Usuario user = new Usuario();
                                        assert response.body() != null;
                                        user.setUsuario(response.body());
                                    } else {
                                        Toast.makeText(Perfil.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Usuario> call, Throwable t) {
                                    Toast.makeText(Perfil.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                                }
                            });


                            //moveTaskToBack(true);
                            //android.os.Process.killProcess(android.os.Process.myPid());
                            //System.exit(1);
                        }
                    })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });
    }

    public void btn_perfil_to_cad (View view){
        Intent it = new Intent(this, Cadastro.class);
        Bundle parametros = new Bundle();
        String status = "MODIFICA_CADASTRO";
        parametros.putString("status_usuario", status);
        parametros.putLong("id_usuario", idUsuario);
        it.putExtras(parametros);
        startActivity(it);
    }
    public void btn_perfil_to_main (View view){
        Intent it = new Intent(this, MainActivity.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        it.putExtras(parametros);
        startActivity(it);
    }
    public void btn_perfil_to_cadservico(View view) {
        Intent it = new Intent(this, Cadastro.class);
        Bundle parametros = new Bundle();
        String status = "CADASTRO_SERVICO";
        parametros.putLong("id_usuario", idUsuario);
        parametros.putString("status_usuario", status);
        it.putExtras(parametros);
        startActivity(it);
    }
}