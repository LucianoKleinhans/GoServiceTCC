package com.lajotasoftware.goservice.Frames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.lajotasoftware.goservice.Entity.Mensagem;
import com.lajotasoftware.goservice.Entity.Proposta;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.API;
import com.lajotasoftware.goservice.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Negociacao extends AppCompatActivity {

    Long idUsuario, idPrestador, idCliente, idProposta;

    API api;
    RetrofitService retrofitService;
    Proposta proposta;
    Mensagem mensagem;

    Intent it;

    List<Mensagem> mensagens = new ArrayList<>();


    MaterialTextView ttvUsernameProposta;
    MaterialTextView ttvCidadeProposta;
    MaterialTextView ttvEmailPerfilProposta;
    MaterialTextView ttvTelefonePerfilProposta;
    MaterialTextView ttvSitePerfilProposta;

    RecyclerView recyclerView;

    TextInputEditText textMensagem;
    TextInputEditText textValorMensagem;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        Bundle parametros = it.getExtras();
        idUsuario = parametros.getLong("id_usuario");
        idPrestador = parametros.getLong("id_prestador");
        idCliente = parametros.getLong("id_cliente");
        setContentView(R.layout.negociacao);
        initializeComponents();
    }

    private void initializeComponents() {
        preencheCabecalho();
        textMensagem.setText("");
        textValorMensagem.setText("");
    }

    private void preencheCabecalho() {
        retrofitService = new RetrofitService();
        retrofitService.getRetrofit().create(API.class);
        if (idUsuario.equals(idCliente)){
            api.getUsuario(idPrestador).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    assert response.body() != null;
                    ttvUsernameProposta.setText(response.body().getPrimeiroNome());
                    ttvCidadeProposta.setText(response.body().getCidade());
                    ttvEmailPerfilProposta.setText(response.body().getEmail());
                    ttvTelefonePerfilProposta.setText(response.body().getTelefone());
                    ttvSitePerfilProposta.setText(response.body().getSite());
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {

                }
            });
        }else if (idUsuario.equals(idPrestador)){
            api.getUsuario(idCliente).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    assert response.body() != null;
                    ttvUsernameProposta.setText(response.body().getPrimeiroNome());
                    ttvCidadeProposta.setText(response.body().getCidade());
                    ttvEmailPerfilProposta.setText(response.body().getEmail());
                    ttvTelefonePerfilProposta.setText(response.body().getTelefone());
                    ttvSitePerfilProposta.setText(response.body().getSite());
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {

                }
            });
        }

    }

    public void btnEnviarMensagem(View view) {
    }

    public void btnVoltarProposta(View view) {
        onBackPressed();
    }
}
