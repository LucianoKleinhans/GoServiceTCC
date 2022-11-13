package com.lajotasoftware.goservice.Frames;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.lajotasoftware.goservice.Adapter.CustomAdapterPedido;
import com.lajotasoftware.goservice.Adapter.CustomAdapterService;
import com.lajotasoftware.goservice.Adapter.CustomAdapterServicePerfilPrestador;
import com.lajotasoftware.goservice.Entity.Categoria;
import com.lajotasoftware.goservice.Entity.Pedido;
import com.lajotasoftware.goservice.Entity.Return;
import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.SubCategoria;
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

    Long idCategoria, idSubCategoria;

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

    Categoria categoria;
    SubCategoria subCategoria;
    List<Categoria> categorias = new ArrayList<>();
    List<SubCategoria> subCategorias = new ArrayList<>();

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
        Spinner spinnerCategoriaServico = findViewById(R.id.spinner_categoria_servicos);
        Spinner spinnerSubCategoriaServico = findViewById(R.id.spinner_sub_categoria_servicos);
        RetrofitService retrofitServiceCategoria = new RetrofitService();
        api = retrofitServiceCategoria.getRetrofit().create(API.class);
        api.getAllCategoria().enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                int aux = 0;
                if (response.body() != null) {
                    aux = response.body().size();
                }
                if (aux > 0) {
                    categorias.clear();
                    for (int i = 1; i <= aux; i++) {
                        categoria = new Categoria();
                        categoria.setCategoria(response.body().get(i - 1));
                        categorias.add(categoria);
                    }
                    ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(getApplicationContext(), android.R.layout.simple_spinner_item, categorias);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategoriaServico.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {

            }
        });
        spinnerCategoriaServico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idCategoria = Long.valueOf(i) + 1;
                RetrofitService retrofitServiceSubCategoria = new RetrofitService();
                api = retrofitServiceSubCategoria.getRetrofit().create(API.class);
                api.getSubCategoria(idCategoria).enqueue(new Callback<List<SubCategoria>>() {
                    @Override
                    public void onResponse(Call<List<SubCategoria>> call, Response<List<SubCategoria>> response) {
                        int aux = 0;
                        if (response.body() != null) {
                            aux = response.body().size();
                        }
                        if (aux > 0) {
                            subCategorias.clear();
                            for (int i = 1; i <= aux; i++) {
                                subCategoria = new SubCategoria();
                                subCategoria.setSubCategoria(response.body().get(i - 1));
                                subCategorias.add(subCategoria);
                            }
                            ArrayAdapter<SubCategoria> adapter = new ArrayAdapter<SubCategoria>(getApplicationContext(), android.R.layout.simple_spinner_item, subCategorias);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerSubCategoriaServico.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SubCategoria>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    spinnerSubCategoriaServico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String subCat = adapterView.getSelectedItem().toString();
            int pos = subCat.indexOf("-");
            idSubCategoria = Long.valueOf(String.copyValueOf(subCat.toCharArray(), 0, pos - 1));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });
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
                        servico.setServico(response.body().get(i -1));
                        servicos.add(servico);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    customAdapter = new CustomAdapterServicePerfilPrestador(Servicos.this, servicos, Servicos.this, "SERVICOS");
                    recyclerView.setAdapter(customAdapter);
                }
                progressBarServicos.setVisibility(View.GONE);
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
        finish();
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
                                RetrofitService retrofitService = new RetrofitService();
                                api = retrofitService.getRetrofit().create(API.class);
                                api.verificaSeExiste(idUsuario, id).enqueue(new Callback<Return>() {
                                    @Override
                                    public void onResponse(Call<Return> call, Response<Return> response) {
                                        if (response.body().getStatusCode() == 100){
                                            pedido = new Pedido();
                                            cliente = new Usuario();
                                            prestador = new Usuario();
                                            servico = new Servico();
                                            cliente.setId(idUsuario);
                                            prestador.setId(idPrestador);
                                            servico.setId(id);
                                            pedido.setId_Cliente(cliente);
                                            pedido.setId_Prestador(prestador);
                                            pedido.setDataEmissao(date.getTime());
                                            pedido.setId_Servico(servico);
                                            pedido.setServicoSolicitado(false);
                                            pedido.setStatus("ABERTO");
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
                                        }else{
                                            Toast.makeText(Servicos.this, "Já possui um pedido seu para esse serviço!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Return> call, Throwable t) {

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

    public void buscarPorCategoria(View view) {
        progressBarServicos.setVisibility(View.VISIBLE);
        servicos.clear();
        RetrofitService retrofitServiceCategoria = new RetrofitService();
        api = retrofitServiceCategoria.getRetrofit().create(API.class);
        api.getAllServicosByCategoria(idCategoria, idSubCategoria).enqueue(new Callback<List<Servico>>() {
            @Override
            public void onResponse(Call<List<Servico>> call, Response<List<Servico>> response) {
                int aux = 0;
                    if (response.body() != null) {
                    aux = response.body().size();
                }
                if (aux > 0) {
                    for (int i = 1; i <= aux; i++) {
                        Servico servico = new Servico();
                        servico.setServico(response.body().get(i -1));
                        servicos.add(servico);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    customAdapter = new CustomAdapterServicePerfilPrestador(Servicos.this, servicos, Servicos.this, "SERVICOS");
                    recyclerView.setAdapter(customAdapter);
                }else{
                    Toast.makeText(Servicos.this, "Nenhum serviço encontrado!", Toast.LENGTH_SHORT).show();
                    listarServicos();
                }
                progressBarServicos.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Servico>> call, Throwable t) {
                Toast.makeText(Servicos.this, "Sem Sucesso ao carregar lista de serviço!", Toast.LENGTH_SHORT).show();
                progressBarServicos.setVisibility(View.GONE);
                listarServicos();
            }
        });
    }
}
