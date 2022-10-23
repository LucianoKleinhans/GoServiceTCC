package com.lajotasoftware.goservice.Frames;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

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

    Long idUsuario, idPrestador, idCliente, idProposta, idCardServico;

    int delay = 2000;   // delay de 20 seg.
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
    RatingBar ratingBarNegociacao;
    ProgressBar progressBarNegociacao;

    Intent it;
    Dialog dialog;

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
        idCardServico = parametros.getLong("id_card_servico");
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
        dialog = new Dialog(this);
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
        ratingBarNegociacao = findViewById(R.id.ratingBarNegociacao);
        progressBarNegociacao = findViewById(R.id.progressBarNegociacao);

        textMensagem = findViewById(R.id.edtTextMensagem);

        progressBarNegociacao.setVisibility(View.VISIBLE);
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
                    ratingBarNegociacao.setRating(response.body().getAvaliacaoPrestador().floatValue());
                    btnAceitarPropostaMensagem.setVisibility(View.VISIBLE);
                    btnAlterarPropostaMensagem.setVisibility(View.INVISIBLE);
                    retrofitService = new RetrofitService();
                    api = retrofitService.getRetrofit().create(API.class);
                    api.getPropostaByID(idProposta).enqueue(new Callback<Proposta>() {
                        @Override
                        public void onResponse(Call<Proposta> call, Response<Proposta> response) {
                            assert response.body() != null;
                            ttvidPropostaMensagem.setText(response.body().getId().toString());
                            ttvValorPropostaMensagem.setText("Valor: R$"+response.body().getValor().toString());
                            ttvDescPropostaMensagem.setText(response.body().getObservacao());
                            progressBarNegociacao.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<Proposta> call, Throwable t) {
                            progressBarNegociacao.setVisibility(View.GONE);
                            onBackPressed();
                        }
                    });
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    progressBarNegociacao.setVisibility(View.GONE);
                    onBackPressed();
                }
            });
        }else if (idUsuario.equals(idPrestador)) {
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
                    ratingBarNegociacao.setRating(response.body().getAvaliacaoCliente().floatValue());
                    btnAceitarPropostaMensagem.setVisibility(View.INVISIBLE);
                    btnAlterarPropostaMensagem.setVisibility(View.VISIBLE);
                    retrofitService = new RetrofitService();
                    api = retrofitService.getRetrofit().create(API.class);
                    api.getPropostaByID(idProposta).enqueue(new Callback<Proposta>() {
                        @Override
                        public void onResponse(Call<Proposta> call, Response<Proposta> response) {
                            assert response.body() != null;
                            ttvidPropostaMensagem.setText(response.body().getId().toString());
                            ttvValorPropostaMensagem.setText("Valor: R$"+response.body().getValor().toString());
                            ttvDescPropostaMensagem.setText(response.body().getObservacao());
                            progressBarNegociacao.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<Proposta> call, Throwable t) {
                            Toast.makeText(Negociacao.this, "Bom dia", Toast.LENGTH_SHORT).show();
                            progressBarNegociacao.setVisibility(View.GONE);
                            onBackPressed();
                        }
                    });
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    progressBarNegociacao.setVisibility(View.GONE);
                    onBackPressed();
                }
            });
        }

        textMensagem.setText("");
        listarMensagem();
    }

    private void listarMensagem() {
        RecyclerView recyclerView = findViewById(R.id.listMensagem);
        retrofitService = new RetrofitService();
        progressBarNegociacao.setVisibility(View.VISIBLE);
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
                progressBarNegociacao.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                progressBarNegociacao.setVisibility(View.GONE);
            }
        });
    }

    public void btnEnviarMensagem(View view) {
        if (!textMensagem.getText().equals("")) {
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
                    textMensagem.setText("");
                    listarMensagem();
                }

                @Override
                public void onFailure(Call<Mensagem> call, Throwable t) {

                }
            });
        }
    }

    public void btnVoltarProposta(View view) {
        onBackPressed();
        finish();
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
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
        dialog.setContentView(R.layout.z_custom_alertdialog_proposta);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        idPrestador = idUsuario;

        MaterialButton btnConfirma = dialog.findViewById(R.id.btnConfirmaProposta);
        TextInputEditText edtDescProposta = dialog.findViewById(R.id.edtDescricaoProposta);
        TextInputEditText edtValorProposta = dialog.findViewById(R.id.edtValorProposta);

        btnConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtDescProposta.toString().equals("")) {
                    if (!edtValorProposta.toString().equals("")) {
                        Proposta proposta = new Proposta();
                        Usuario prestador = new Usuario();
                        Usuario cliente = new Usuario();
                        SolicitaServico card = new SolicitaServico();

                        prestador.setId(idPrestador);
                        cliente.setId(idCliente);
                        card.setId(idCardServico);

                        proposta.setId_Prestador(prestador);
                        proposta.setId_Cliente(cliente);
                        proposta.setId_SolicitaServico(card);
                        proposta.setObservacao(edtDescProposta.getText().toString());
                        proposta.setValor(Double.valueOf(edtValorProposta.getText().toString()));
                        proposta.setStatus("ABERTO");

                        RetrofitService retrofitService = new RetrofitService();
                        retrofitService.getRetrofit().create(API.class);
                        api.updateProposta(idProposta,proposta).enqueue(new Callback<Proposta>() {
                            @Override
                            public void onResponse(Call<Proposta> call, Response<Proposta> response) {
                                Toast.makeText(Negociacao.this, "Proposta Atualizada com Sucesso!", Toast.LENGTH_SHORT).show();
                                Mensagem mensagem = new Mensagem();
                                Proposta proposta = new Proposta();
                                Usuario prestador = new Usuario();
                                Usuario cliente = new Usuario();
                                Usuario usuario = new Usuario();
                                proposta.setId(idProposta);
                                cliente.setId(idCliente);
                                prestador.setId(idPrestador);
                                usuario.setId(idUsuario);
                                mensagem.setMensagem("Proposta Atualizada!");
                                mensagem.setId_Proposta(proposta);
                                mensagem.setId_Cliente(cliente);
                                mensagem.setId_Prestador(prestador);
                                mensagem.setSendBy(usuario);
                                RetrofitService retrofitService = new RetrofitService();
                                api = retrofitService.getRetrofit().create(API.class);
                                api.createMensagem(mensagem).enqueue(new Callback<Mensagem>() {
                                    @Override
                                    public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
                                        listarMensagem();
                                        onBackPressed();
                                    }

                                    @Override
                                    public void onFailure(Call<Mensagem> call, Throwable t) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Proposta> call, Throwable t) {

                            }
                        });
                    } else {
                        Toast.makeText(Negociacao.this, "Valor da proposta é obrigatório!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Negociacao.this, "Descrição da proposta é obrigatória!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    public void messageClick(View view) {
        atualizarProposta();
    }

    private void atualizarProposta() {
        retrofitService = new RetrofitService();
        progressBarNegociacao.setVisibility(View.VISIBLE);
        api = retrofitService.getRetrofit().create(API.class);
        api.getPropostaByID(idProposta).enqueue(new Callback<Proposta>() {
            @Override
            public void onResponse(Call<Proposta> call, Response<Proposta> response) {
                assert response.body() != null;
                ttvidPropostaMensagem.setText(response.body().getId().toString());
                ttvValorPropostaMensagem.setText("Valor: R$"+response.body().getValor().toString());
                ttvDescPropostaMensagem.setText(response.body().getObservacao());
                progressBarNegociacao.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Proposta> call, Throwable t) {
                progressBarNegociacao.setVisibility(View.GONE);
            }
        });
    }

    public void refreshProposta(View view) {
        atualizarProposta();
    }
}
