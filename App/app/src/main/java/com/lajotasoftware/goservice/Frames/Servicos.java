package com.lajotasoftware.goservice.Frames;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.lajotasoftware.goservice.Adapter.CustomAdapterPedido;
import com.lajotasoftware.goservice.Adapter.CustomAdapterService;
import com.lajotasoftware.goservice.Adapter.CustomAdapterServicePerfilPrestador;
import com.lajotasoftware.goservice.Entity.Pedido;
import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.MainActivity;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.API;
import com.lajotasoftware.goservice.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Servicos extends AppCompatActivity implements CustomAdapterServicePerfilPrestador.OnServicoListener{

    private Long idUsuario;

    Intent it;

    CustomAdapterServicePerfilPrestador customAdapter;
    RecyclerView recyclerView;
    List<Servico> servicos = new ArrayList<>();

    RetrofitService retrofitService;
    API api;
    Servico servico;
    Pedido pedido;
    Usuario prestador;
    Usuario cliente;
    Date date = new Date();
    ProgressBar progressBarServicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        Bundle parametros = it.getExtras();
        idUsuario = parametros.getLong("id_usuario");
        setContentView(R.layout.servicos);
        initializeComponents();
    }

    private void initializeComponents() {
        recyclerView = findViewById(R.id.listaServicos);
        progressBarServicos = findViewById(R.id.progressBarServicos);
        progressBarServicos.setVisibility(View.VISIBLE);
        listarServicos();
    }

    private void listarServicos() {
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getAllServicos(idUsuario).enqueue(new Callback<List<Servico>>() {
            @Override
            public void onResponse(Call<List<Servico>> call, Response<List<Servico>> response) {
                int aux = 0;
                if (response.body() != null) {
                    aux = response.body().size();
                }
                if (aux > 0) {
                    for (int i = 1; i <= aux; i++) {
                        Servico servico = new Servico();
                        servico.setId(response.body().get(i - 1).getId());
                        servico.setId_Prestador(response.body().get(i - 1).getId_Prestador());
                        servico.setNome(response.body().get(i - 1).getNome());
                        servico.setObsServico(response.body().get(i - 1).getObsServico());
                        servico.setValor(response.body().get(i - 1).getValor());
                        servicos.add(servico);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    customAdapter = new CustomAdapterServicePerfilPrestador(Servicos.this, servicos, Servicos.this);
                    recyclerView.setAdapter(customAdapter);
                    progressBarServicos.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Servico>> call, Throwable t) {
                Toast.makeText(Servicos.this, "Sem Sucesso ao carregar lista de serviço!", Toast.LENGTH_SHORT).show();
                progressBarServicos.setVisibility(View.GONE);
            }
        });
    }

    public void btn_servicos_to_main(View view) {
        Intent it = new Intent(this, MainActivity.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        it.putExtras(parametros);
        startActivity(it);
    }

    @Override
    public void SolicitaServico(int position, Long id, Long idPrestador) {
        AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Solicitar Serviço");
        alertDialogBuilder
                .setMessage("Clique sim para solicitar esse serviço!")
                .setCancelable(false)
                .setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int idd) {
                                pedido = new Pedido();
                                cliente = new Usuario();
                                servico = new Servico();
                                prestador = new Usuario();
                                cliente.setId(idUsuario);
                                servico.setId(id);
                                prestador.setId(idPrestador);
                                pedido.setId_Cliente(cliente);
                                pedido.setId_Prestador(prestador);
                                pedido.setDataEmissao(date.getTime());
                                pedido.setId_Servico(servico);
                                pedido.setServicoSolicitado(true);
                                pedido.setStatus("ABERTO");
                                RetrofitService retrofitService = new RetrofitService();
                                api = retrofitService.getRetrofit().create(API.class);
                                api.criarPedido(pedido).enqueue(new Callback<Pedido>() {
                                    @Override
                                    public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                                        Toast.makeText(Servicos.this,"Serviço solicitado!", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Pedido> call, Throwable t) {
                                        Toast.makeText(Servicos.this,"Não foi possivel solicitar esse serviço!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
