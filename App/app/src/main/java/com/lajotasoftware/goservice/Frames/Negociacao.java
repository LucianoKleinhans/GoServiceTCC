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
import com.lajotasoftware.goservice.Entity.Return;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.API;
import com.lajotasoftware.goservice.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Negociacao extends AppCompatActivity {

    Long idUsuario, idPrestador, idCliente, idProposta, idCardServico;

    int delay = 1000;   // delay de 10000 seg.
    int interval = 10000;  // intervalo de 1 seg.

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
    Timer timer;

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
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                listarMensagem();
            }
        }, delay, interval);
    }

    @Override
    protected void onPause() {
        timer.cancel();
        super.onPause();
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
                recyclerView.scrollToPosition(mensagens.size() - 1);
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {
            }
        });
    }

    public void btnEnviarMensagem(View view) {
        if (!textMensagem.getText().equals("")) {
            Date date = new Date();
            Long timeMilli = date.getTime();

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
            mensagem.setDataHoraMsg(timeMilli);

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
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        onPause();
        finish();
    }

    public void aceitarPropostaMensagem(View view) {
        Date date = new Date();
        Long timeMilli = date.getTime();
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
        mensagem.setDataHoraMsg(timeMilli);
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

        proposta = new Proposta();
        SolicitaServico solicitaServico = new SolicitaServico();
        solicitaServico.setId(idCardServico);
        proposta.setStatus("ACEITO");
        proposta.setId_SolicitaServico(solicitaServico);
        Long idPropostaAceita = idProposta;
        RetrofitService retrofitService = new RetrofitService();
        retrofitService.getRetrofit().create(API.class);
        api.updateProposta(idProposta, proposta).enqueue(new Callback<Proposta>() {
            @Override
            public void onResponse(Call<Proposta> call, Response<Proposta> response) {
                Toast.makeText(Negociacao.this, "Proposta Aceita!", Toast.LENGTH_SHORT).show();
                RetrofitService retrofitService = new RetrofitService();
                retrofitService.getRetrofit().create(API.class);
                api.getPropostasRecebidas(idCardServico).enqueue(new Callback<List<Proposta>>() {
                    @Override
                    public void onResponse(Call<List<Proposta>> call, Response<List<Proposta>> response) {
                        int aux = 0;
                        Long idPro;
                        if (response.body() != null) {
                            aux = response.body().size();
                        }
                        if (aux > 0) {
                            //propostas.clear();
                            for (int i = 1; i <= aux; i++) {
                                if (!response.body().get(i - 1).getId().equals(idPropostaAceita)){
                                    proposta = new Proposta();
                                    proposta.setStatus("RECUSADO");
                                    idPro = response.body().get(i - 1).getId();
                                    RetrofitService retrofitService = new RetrofitService();
                                    retrofitService.getRetrofit().create(API.class);
                                    api.updateProposta(idPro, proposta).enqueue(new Callback<Proposta>() {
                                        @Override
                                        public void onResponse(Call<Proposta> call, Response<Proposta> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<Proposta> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                            RetrofitService retrofitService = new RetrofitService();
                            retrofitService.getRetrofit().create(API.class);
                            SolicitaServico card = new SolicitaServico();
                            card.setStatus("FINALIZADO");
                            api.updateCardServico(idCardServico,card).enqueue(new Callback<SolicitaServico>() {
                                @Override
                                public void onResponse(Call<SolicitaServico> call, Response<SolicitaServico> response) {
                                    Intent it = new Intent(Negociacao.this, Pedidos.class);
                                    Bundle parametros = new Bundle();
                                    parametros.putLong("id_usuario", idUsuario);
                                    parametros.putLong("id_proposta", idPropostaAceita);
                                    parametros.putLong("id_prestador", idPrestador);
                                    parametros.putString("status", "PROPOSTA_ACEITA");
                                    it.putExtras(parametros);
                                    startActivity(it);
                                }

                                @Override
                                public void onFailure(Call<SolicitaServico> call, Throwable t) {

                                }
                            });
                        } else {
                            RetrofitService retrofitService = new RetrofitService();
                            retrofitService.getRetrofit().create(API.class);
                            SolicitaServico card = new SolicitaServico();
                            card.setStatus("FINALIZADO");
                            api.updateCardServico(idCardServico,card).enqueue(new Callback<SolicitaServico>() {
                                @Override
                                public void onResponse(Call<SolicitaServico> call, Response<SolicitaServico> response) {
                                    Intent it = new Intent(Negociacao.this, Pedidos.class);
                                    Bundle parametros = new Bundle();
                                    parametros.putLong("id_usuario", idUsuario);
                                    parametros.putLong("id_proposta", idPropostaAceita);
                                    parametros.putLong("id_prestador", idPrestador);
                                    parametros.putString("status", "PROPOSTA_ACEITA");
                                    it.putExtras(parametros);
                                    startActivity(it);
                                }

                                @Override
                                public void onFailure(Call<SolicitaServico> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Proposta>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Proposta> call, Throwable t) {

            }
        });
        api.setStatusProposta(idProposta,"ACEITA").enqueue(new Callback<Return>() {
            @Override
            public void onResponse(Call<Return> call, Response<Return> response) {

            }

            @Override
            public void onFailure(Call<Return> call, Throwable t) {

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

                        proposta.setId(idProposta);
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
                                Date date = new Date();
                                Long timeMilli = date.getTime();
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
                                mensagem.setDataHoraMsg(timeMilli);
                                RetrofitService retrofitService = new RetrofitService();
                                api = retrofitService.getRetrofit().create(API.class);
                                api.createMensagem(mensagem).enqueue(new Callback<Mensagem>() {
                                    @Override
                                    public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
                                        listarMensagem();
                                        atualizarProposta();
                                    }

                                    @Override
                                    public void onFailure(Call<Mensagem> call, Throwable t) {
                                        Toast.makeText(Negociacao.this, "Falha ao atualizar a proposta!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dialog.hide();
                            }

                            @Override
                            public void onFailure(Call<Proposta> call, Throwable t) {
                                Toast.makeText(Negociacao.this, "Falha ao atualizar a proposta!", Toast.LENGTH_SHORT).show();
                                dialog.hide();
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
