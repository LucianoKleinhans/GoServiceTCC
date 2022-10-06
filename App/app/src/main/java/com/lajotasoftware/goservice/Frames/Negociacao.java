package com.lajotasoftware.goservice.Frames;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.lajotasoftware.goservice.Adapter.CustomAdapterCard;
import com.lajotasoftware.goservice.Adapter.CustomAdapterMensagem;
import com.lajotasoftware.goservice.Adapter.CustomAdapterMensagem.ViewHolder;
import com.lajotasoftware.goservice.Entity.Mensagem;
import com.lajotasoftware.goservice.Entity.Proposta;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.API;
import com.lajotasoftware.goservice.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Negociacao extends AppCompatActivity {

    Long idUsuario, idPrestador, idCliente, idProposta;

    int delay = 5000;   // delay de 5 seg.
    int interval = 1000;  // intervalo de 1 seg.

    API api;
    RetrofitService retrofitService;
    Proposta proposta;
    Mensagem mensagem;
    Usuario usuario;
    Usuario prestador;
    Usuario cliente;

    MaterialTextView ttvUsernameProposta;
    MaterialTextView ttvCidadeProposta;
    MaterialTextView ttvEmailPerfilProposta;
    MaterialTextView ttvTelefonePerfilProposta;
    MaterialTextView ttvSitePerfilProposta;
    MaterialTextView ttvDescPropostaMensagem;
    MaterialTextView ttvValorPropostaMensagem;
    MaterialTextView ttvidPropostaMensagem;
    MaterialButton btnAceitarPropostaMensagem;
    MaterialButton btnAlterarPropostaMensagem;

    Intent it;

    List<Mensagem> mensagens = new ArrayList<>();

    CustomAdapterMensagem customAdapterMensagem;
    TextInputEditText textMensagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        Bundle parametros = it.getExtras();
        idUsuario = parametros.getLong("id_usuario");
        idPrestador = parametros.getLong("id_prestador");
        idCliente = parametros.getLong("id_cliente");
        idProposta = parametros.getLong("id_proposta");
        setContentView(R.layout.negociacao);
        initializeComponents();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                listarMensagem();
            }
        }, delay, interval);
    }

    private void initializeComponents() {
        ttvUsernameProposta = findViewById(R.id.ttvUsernameProposta);
        ttvCidadeProposta = findViewById(R.id.ttvCidadeProposta);
        ttvEmailPerfilProposta = findViewById(R.id.ttvEmailPerfilProposta);
        ttvTelefonePerfilProposta = findViewById(R.id.ttvTelefonePerfilProposta);
        ttvSitePerfilProposta = findViewById(R.id.ttvSitePerfilProposta);
        ttvDescPropostaMensagem = findViewById(R.id.ttvDescPropostaMensagem);
        ttvValorPropostaMensagem = findViewById(R.id.ttvValorPropostaMensagem);
        ttvidPropostaMensagem = findViewById(R.id.idPropostaMensagem);
        btnAceitarPropostaMensagem = findViewById(R.id.btnAceitarPropostaMensagem);
        btnAlterarPropostaMensagem = findViewById(R.id.btnAlterarPropostaMensagem);

        textMensagem = findViewById(R.id.edtTextMensagem);

        if (idUsuario.equals(idCliente)){
            retrofitService = new RetrofitService();
            api = retrofitService.getRetrofit().create(API.class);
            api.getUsuario(idPrestador).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    assert response.body() != null;
                    ttvUsernameProposta.setText(response.body().getPrimeiroNome());
                    ttvCidadeProposta.setText(response.body().getCidade());
                    ttvEmailPerfilProposta.setText(response.body().getEmail());
                    ttvTelefonePerfilProposta.setText(response.body().getTelefone());
                    ttvSitePerfilProposta.setText(response.body().getSite());
                    btnAceitarPropostaMensagem.setVisibility(View.VISIBLE);
                    btnAlterarPropostaMensagem.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {

                }
            });
        }else if (idUsuario.equals(idPrestador)){
            retrofitService = new RetrofitService();
            api = retrofitService.getRetrofit().create(API.class);
            api.getUsuario(idCliente).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    assert response.body() != null;
                    ttvUsernameProposta.setText(response.body().getPrimeiroNome());
                    ttvCidadeProposta.setText(response.body().getCidade());
                    ttvEmailPerfilProposta.setText(response.body().getEmail());
                    ttvTelefonePerfilProposta.setText(response.body().getTelefone());
                    ttvSitePerfilProposta.setText(response.body().getSite());
                    btnAceitarPropostaMensagem.setVisibility(View.INVISIBLE);
                    btnAlterarPropostaMensagem.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                }
            });
        }
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getPropostaByID(idProposta).enqueue(new Callback<Proposta>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Proposta> call, Response<Proposta> response) {
                assert response.body() != null;
                ttvidPropostaMensagem.setText(response.body().getId().toString());
                ttvValorPropostaMensagem.setText("Valor: R$"+response.body().getValor().toString());
                ttvDescPropostaMensagem.setText(response.body().getObservacao());
            }

            @Override
            public void onFailure(Call<Proposta> call, Throwable t) {

            }
        });

        textMensagem.setText("");
        listarMensagem();
    }

    private void listarMensagem() {
        RecyclerView recyclerView = findViewById(R.id.listMensagem);
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getPropostaMensagem(idProposta).enqueue(new Callback<List<Mensagem>>() {
            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                int aux = 0;
                if (response.body() != null) {
                    aux = response.body().size();
                }
                mensagens.clear();
                for (int i = 1; i <= aux; i++) {
                    mensagem = new Mensagem();
                    mensagem.setMessagem(response.body().get(i - 1));
                    mensagens.add(mensagem);
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                customAdapterMensagem = new CustomAdapterMensagem(Negociacao.this, mensagens, idUsuario);
                recyclerView.setAdapter(customAdapterMensagem);
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {

            }
        });
    }

    public void btnEnviarMensagem(View view) {
        mensagem = new Mensagem();
        proposta = new Proposta();
        prestador = new Usuario();
        cliente = new Usuario();
        usuario = new Usuario();
        proposta.setId(idProposta);
        cliente.setId(idCliente);
        prestador.setId(idPrestador);
        usuario.setId(idUsuario);
        mensagem.setMensagem(String.valueOf(textMensagem.getText()));
        mensagem.setId_Proposta(proposta);
        mensagem.setId_Cliente(cliente);
        mensagem.setId_Prestador(prestador);
        mensagem.setSendBy(usuario);

        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.createMensagem(mensagem).enqueue(new Callback<Mensagem>() {
            @Override
            public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
                listarMensagem();
            }

            @Override
            public void onFailure(Call<Mensagem> call, Throwable t) {

            }
        });
    }

    public void btnVoltarProposta(View view) {
        onBackPressed();
    }

    public void aceitarPropostaMensagem(View view) {
        idProposta = Long.parseLong((String) ttvidPropostaMensagem.getText());
        mensagem = new Mensagem();
        proposta = new Proposta();
        prestador = new Usuario();
        cliente = new Usuario();
        usuario = new Usuario();
        proposta.setId(idProposta);
        cliente.setId(idCliente);
        prestador.setId(idPrestador);
        usuario.setId(idUsuario);
        mensagem.setMensagem("Proposta aceita!");
        mensagem.setId_Proposta(proposta);
        mensagem.setId_Cliente(cliente);
        mensagem.setId_Prestador(prestador);
        mensagem.setSendBy(usuario);
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.createMensagem(mensagem).enqueue(new Callback<Mensagem>() {
            @Override
            public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
                listarMensagem();
            }

            @Override
            public void onFailure(Call<Mensagem> call, Throwable t) {

            }
        });
    }

    public void alterarPropostaMensagem(View view) {
        idProposta = Long.parseLong((String) ttvidPropostaMensagem.getText());

    }
}
