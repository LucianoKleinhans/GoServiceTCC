package com.lajotasoftware.goservice.Frames;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.lajotasoftware.goservice.Adapter.CustomAdapterPedido;
import com.lajotasoftware.goservice.Adapter.CustomAdapterPrestadores;
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

public class Pedidos extends AppCompatActivity implements CustomAdapterPedido.OnPedidoListener{

    private Long idUsuario, idPrestador;
    String status;
    Intent it;

    CustomAdapterPedido customAdapter;
    RecyclerView recyclerView;
    List<Pedido> pedidos = new ArrayList<>();

    RetrofitService retrofitService;
    API api;
    Pedido pedido;
    Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        Bundle parametros = it.getExtras();
        idUsuario = parametros.getLong("id_usuario");
        setContentView(R.layout.solicitacoes);
        initializeComponents();
    }

    private void initializeComponents() {
        recyclerView = findViewById(R.id.listaPedidos);
        listarPedidos();
    }

    private void listarPedidos() {
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getPedidosPrestador(idUsuario).enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                int aux = response.body().size();
                pedidos.clear();
                if (aux > 0) {
                    for (int i = 1; i <= aux; i++) {
                        pedido = new Pedido();
                        pedido.setId(response.body().get(i - 1).getId());
                        pedido.setId_Cliente(response.body().get(i - 1).getId_Cliente());
                        pedido.setId_Servico(response.body().get(i - 1).getId_Servico());
                        pedidos.add(pedido);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    customAdapter = new CustomAdapterPedido(Pedidos.this, pedidos, Pedidos.this);
                    recyclerView.setAdapter(customAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Toast.makeText(Pedidos.this, "Sem Sucesso ao carregar lista de pedidos!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void AceitaPedido(int position, Long id) {
        
    }

    @Override
    public void RecusaPedido(int position, Long id) {

    }

    public void btn_cards_to_main(View view) {
        Intent it = new Intent(this, MainActivity.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        it.putExtras(parametros);
        startActivity(it);
    }
}
