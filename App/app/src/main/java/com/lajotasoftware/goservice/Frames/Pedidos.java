package com.lajotasoftware.goservice.Frames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lajotasoftware.goservice.Adapter.CustomAdapterPedido;
import com.lajotasoftware.goservice.Entity.Pedido;
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
    RecyclerView recyclerViewRecebida;
    RecyclerView recyclerViewEnviada;
    RecyclerView recyclerViewAberta;
    List<Pedido> pedidos = new ArrayList<>();

    RetrofitService retrofitService;
    API api;
    Pedido pedido;
    Date date = new Date();

    TabLayout tabLayout;
    //ViewPager2 viewPager2;
    //PageAdapterSolicitacoes pageAdapterSolicitacoes;

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
        recyclerViewRecebida = findViewById(R.id.listaPedidoRecebido);
        recyclerViewEnviada = findViewById(R.id.listaPedidoEnviado);

        tabLayout=findViewById(R.id.tabLayout);
        //viewPager2=findViewById(R.id.view_pager);
        //pageAdapterSolicitacoes = new PageAdapterSolicitacoes(this);
        //viewPager2.setAdapter(pageAdapterSolicitacoes);
        SolicitacaoRecebida();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager2.setCurrentItem(tab.getPosition());
                int position = tab.getPosition();
                switch (position){
                    case 0:
                        SolicitacaoRecebida();
                    case 1:
                        SolicitacaoEnviada();
                    case 2:
                        //SolicitacaoAberta();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
/*        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });*/


    }

    private void SolicitacaoRecebida() {
        recyclerViewEnviada.setVisibility(View.INVISIBLE);
        //recyclerViewAberta.setVisibility(View.INVISIBLE);//aberta
        recyclerViewRecebida.setVisibility(View.VISIBLE);
        recyclerViewRecebida = findViewById(R.id.listaPedidoRecebido);
        listarPedidos();
    }

    private void SolicitacaoEnviada() {
        recyclerViewRecebida.setVisibility(View.INVISIBLE);
        //recyclerViewAberta.setVisibility(View.INVISIBLE);//aberta
        recyclerViewEnviada.setVisibility(View.VISIBLE);
        recyclerViewEnviada = findViewById(R.id.listaPedidoEnviado);
        //listarPedidos();
    }

    /*private void SolicitacaoAberta() {
        recyclerViewRecebida.setVisibility(View.INVISIBLE);
        //recyclerViewAberta.setVisibility(View.INVISIBLE);//aberta
        recyclerViewEnviada.setVisibility(View.INVISIBLE);
        //recyclerViewAberta = findViewById(R.id.listaPedidoAberto);
        //listarPedidos();
    }*/

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
                    recyclerViewRecebida.setLayoutManager(linearLayoutManager);
                    customAdapter = new CustomAdapterPedido(Pedidos.this, pedidos, Pedidos.this);
                    recyclerViewRecebida.setAdapter(customAdapter);
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
