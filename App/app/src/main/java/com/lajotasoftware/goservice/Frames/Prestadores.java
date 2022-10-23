package com.lajotasoftware.goservice.Frames;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.lajotasoftware.goservice.Adapter.CustomAdapterPrestadores;
import com.lajotasoftware.goservice.Adapter.CustomAdapterServicePerfilPrestador;
import com.lajotasoftware.goservice.Entity.Pedido;
import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.MainActivity;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.RetrofitService;
import com.lajotasoftware.goservice.retrofit.API;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Prestadores extends AppCompatActivity implements CustomAdapterPrestadores.OnPrestadorListener, CustomAdapterServicePerfilPrestador.OnServicoListener{

    private Long idUsuario, idPrestador;
    String status;

    CustomAdapterPrestadores customAdapter;
    RecyclerView recyclerView;
    List<Usuario> prestadores = new ArrayList<>();

    CustomAdapterServicePerfilPrestador customAdapterServicePerfilPrestador;
    RecyclerView recyclerViewServicePrestador;
    List<Servico> servicosPrestador = new ArrayList<>();

    Usuario prestador;
    Usuario cliente;
    Servico servico;
    RetrofitService retrofitService;
    Pedido pedido;
    API api;

    ProgressBar progressBarListPrestadores;

    Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        Bundle parametros = it.getExtras();
        idUsuario = parametros.getLong("id_usuario");
        status = parametros.getString("status");
        setContentView(R.layout.prestadores);
        initializeComponents();
    }

    private void initializeComponents() {
        recyclerView = findViewById(R.id.listaPrestadores);
        progressBarListPrestadores = findViewById(R.id.progressBarListPrestadores);
        listarPrestadores();
    }

    private void listarPrestadores() {
        progressBarListPrestadores.setVisibility(View.VISIBLE);
        RetrofitService retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getAllPrestadores(idUsuario).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                int aux = response.body().size();
                prestadores.clear();
                if (aux > 0) {
                    for (int i = 1; i <= aux; i++) {
                        Usuario prestador = new Usuario();
                        prestador.setId(response.body().get(i - 1).getId());
                        prestador.setPrimeiroNome(response.body().get(i - 1).getPrimeiroNome());
                        prestador.setBio(response.body().get(i - 1).getBio());
                        prestador.setAvaliacaoPrestador(response.body().get(i - 1).getAvaliacaoPrestador());
                        prestadores.add(prestador);
                    }
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(linearLayoutManager1);
                    customAdapter = new CustomAdapterPrestadores(Prestadores.this, prestadores, Prestadores.this);
                    recyclerView.setAdapter(customAdapter);
                    progressBarListPrestadores.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(Prestadores.this, "Sem Sucesso ao carregar lista de serviço!", Toast.LENGTH_SHORT).show();
                progressBarListPrestadores.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void SelecionaPrestador(int position, Long id) {
        status = "VISUALIZAR_PRESTADOR";
        setContentView(R.layout.perfil_prestador);
        initializeComponentsVisualizarPrestador(id);
    }

    private void initializeComponentsVisualizarPrestador(Long id) {
        MaterialTextView textViewNomeUsuario = findViewById(R.id.ttvUsernamePerfilPrestador);
        MaterialTextView textViewCidadeUsuario = findViewById(R.id.ttvCidadePerfilPrestador);
        MaterialTextView textViewEmailUsuario = findViewById(R.id.ttvEmailPerfilPrestador);
        MaterialTextView textViewSiteUsuario = findViewById(R.id.ttvSitePerfilPrestador);
        MaterialTextView textViewBioUsuario = findViewById(R.id.ttvBioPerfilPrestador);
        RatingBar ratingBarPefilPrestador = findViewById(R.id.ratingBarPefilPrestador1);
        ProgressBar progressBarPerfilPrestador = findViewById(R.id.progressBarPerfilPrestador);

        recyclerViewServicePrestador = findViewById(R.id.listServicosPrestador);

        idPrestador = id;

        progressBarPerfilPrestador.setVisibility(View.VISIBLE);
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getPrestador(id).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                prestador = new Usuario();
                assert response.body() != null;
                prestador.setUsuario(response.body());
                textViewNomeUsuario.setText(prestador.getPrimeiroNome());
                textViewCidadeUsuario.setText("Cidade:" + prestador.getCidade() + " - " + prestador.getUf());
                textViewEmailUsuario.setText("E-mail:" + prestador.getEmail());
                if(prestador.getSite() == null){
                    textViewSiteUsuario.setVisibility(View.INVISIBLE);
                }else{textViewSiteUsuario.setText("Site:" + prestador.getSite());}
                textViewBioUsuario.setText(prestador.getBio());
                ratingBarPefilPrestador.setRating(prestador.getAvaliacaoPrestador().floatValue());

                listarServicosPrestador(id);
                progressBarPerfilPrestador.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                progressBarPerfilPrestador.setVisibility(View.GONE);
                throw new Error("USUARIO INVALIDO");
            }
        });
    }

    private void listarServicosPrestador(Long id) {
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getServicosPrestador(id).enqueue(new Callback<List<Servico>>() {
            @Override
            public void onResponse(Call<List<Servico>> call, Response<List<Servico>> response) {
                int aux = response.body().size();
                if (aux > 0) {
                    servicosPrestador.clear();
                    for (int i = 1; i <= aux; i++) {
                        servico = new Servico();
                        servico.setId(response.body().get(i - 1).getId());
                        servico.setNome(response.body().get(i - 1).getNome());
                        servico.setObsServico(response.body().get(i - 1).getObsServico());
                        servico.setValor(response.body().get(i - 1).getValor());
                        servico.setId_Prestador(response.body().get(i - 1).getId_Prestador());
                        servicosPrestador.add(servico);
                    }
                    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
                    recyclerViewServicePrestador.setLayoutManager(linearLayoutManager2);
                    customAdapterServicePerfilPrestador = new CustomAdapterServicePerfilPrestador(Prestadores.this, servicosPrestador, Prestadores.this);
                    recyclerViewServicePrestador.setAdapter(customAdapterServicePerfilPrestador);
                }
            }
            @Override
            public void onFailure(Call<List<Servico>> call, Throwable t) {
                Toast.makeText(Prestadores.this, "Sem Sucesso ao carregar lista de serviço!", Toast.LENGTH_SHORT).show();
            }
        });
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
                                cliente.setId(idUsuario);
                                servico.setId(id);
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
                                        Toast.makeText(Prestadores.this,"Serviço solicitado!", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Pedido> call, Throwable t) {
                                        Toast.makeText(Prestadores.this,"Não foi possivel solicitar esse serviço!", Toast.LENGTH_SHORT).show();
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

    public void btn_perfilprestador_to_main(View view) {
        Intent it = new Intent(this, MainActivity.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        it.putExtras(parametros);
        startActivity(it);
    }
    public void btn_prestadores_to_main (View view){
        Intent it = new Intent(this, MainActivity.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        it.putExtras(parametros);
        startActivity(it);
    }
    public void onBackPressed () {
        if (status.equals("VISUALIZAR_PRESTADOR")){
            status = "DEFAUT";
            setContentView(R.layout.prestadores);
            initializeComponents();
        }else{
            Intent it = new Intent(this, MainActivity.class);
            Bundle parametros = new Bundle();
            parametros.putLong("id_usuario", idUsuario);
            it.putExtras(parametros);
            startActivity(it);
        }
    }
}
