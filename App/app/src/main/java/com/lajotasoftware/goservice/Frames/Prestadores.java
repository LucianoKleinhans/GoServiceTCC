package com.lajotasoftware.goservice.Frames;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lajotasoftware.goservice.Adapter.CustomAdapterCard;
import com.lajotasoftware.goservice.Adapter.CustomAdapterPrestadores;
import com.lajotasoftware.goservice.Adapter.CustomAdapterService;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.RetrofitService;
import com.lajotasoftware.goservice.retrofit.UsuarioAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Prestadores extends AppCompatActivity implements CustomAdapterPrestadores.OnPrestadorListener {

    private Long idUsuario;
    Intent it;

    CustomAdapterPrestadores customAdapter;
    RecyclerView recyclerView;
    List<Usuario> prestadores = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        Bundle parametros = it.getExtras();
        idUsuario = parametros.getLong("id_usuario");
        setContentView(R.layout.prestadores);
        initializeComponents();
    }

    private void initializeComponents() {
        recyclerView = findViewById(R.id.listaPrestadores);
        listarCards();
    }

    private void listarCards() {
        RetrofitService retrofitService = new RetrofitService();
        UsuarioAPI usuarioAPI = retrofitService.getRetrofit().create(UsuarioAPI.class);
        usuarioAPI.getAllPrestadores(idUsuario).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                int aux = response.body().size();
                prestadores.clear();
                for (int i=1; i<=aux;i++){
                    Usuario prestador = new Usuario();
                    prestador.setId(response.body().get(i-1).getId());
                    prestador.setPrimeiroNome(response.body().get(i-1).getPrimeiroNome());
                    prestador.setBio(response.body().get(i-1).getBio());
                    prestadores.add(prestador);
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                customAdapter = new CustomAdapterPrestadores(Prestadores.this, prestadores, Prestadores.this);
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(Prestadores.this, "Sem Sucesso ao carregar lista de serviço!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
