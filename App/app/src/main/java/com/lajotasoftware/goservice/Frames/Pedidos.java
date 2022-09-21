package com.lajotasoftware.goservice.Frames;

import static android.graphics.Color.TRANSPARENT;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.lajotasoftware.goservice.Adapter.CustomAdapterPedido;
import com.lajotasoftware.goservice.Entity.Pedido;
import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.Functions.Function;
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

    private Long idUsuario, idPrestador, idPedido;
    String status, parametro;
    Intent it;
    Dialog dialog;

    CustomAdapterPedido customAdapter;
    RecyclerView recyclerView;
    List<Pedido> pedidos = new ArrayList<>();
    MaterialButton btnEnviadas;
    MaterialButton btnRecebidas;
    MaterialButton btnEmProgresso;
    MaterialButton btnFinalizado;

    String nomeCliente,nomePrestador,telefone,servico,descservico,valor;

    RetrofitService retrofitService;
    API api;
    Pedido pedido;
    Function function;
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
        dialog = new Dialog(this);
        recyclerView = findViewById(R.id.listaPedido);
        btnEnviadas = findViewById(R.id.btnPedidosEnviados);
        btnRecebidas = findViewById(R.id.btnPedidosRecebidas);
        btnEmProgresso = findViewById(R.id.btnPedidosProgresso);
        btnFinalizado = findViewById(R.id.btnPedidosFinalizados);
        lista();
    }

    private void lista() {
        parametro = "ENVIADAS";
        btnEnviadas.setBackgroundColor(Color.parseColor("#204c6a"));
        btnRecebidas.setBackgroundColor(Color.parseColor("#153246"));
        btnEmProgresso.setBackgroundColor(Color.parseColor("#153246"));
        btnFinalizado.setBackgroundColor(Color.parseColor("#153246"));
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getPedidosCliente(idUsuario).enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                int aux = 0;
                if (response.body()!=null) {
                    aux = response.body().size();
                }
                pedidos.clear();
                recyclerView.getRecycledViewPool().clear();
                if (aux > 0) {
                    for (int i = 1; i <= aux; i++) {
                        pedido = new Pedido();
                        pedido.setId(response.body().get(i - 1).getId());
                        pedido.setId_Cliente(response.body().get(i - 1).getId_Cliente());
                        pedido.setId_Servico(response.body().get(i - 1).getId_Servico());
                        pedido.setId_Prestador(response.body().get(i - 1).getId_Prestador());
                        pedidos.add(pedido);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    customAdapter = new CustomAdapterPedido(Pedidos.this, pedidos, Pedidos.this, parametro);
                    recyclerView.setAdapter(customAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Toast.makeText(Pedidos.this, "Sem Sucesso ao carregar lista de pedidos!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btnListaPedidoEnviada(View view) {
        parametro = "ENVIADAS";

        btnEnviadas.setBackgroundColor(Color.parseColor("#204c6a"));
        btnRecebidas.setBackgroundColor(Color.parseColor("#153246"));
        btnEmProgresso.setBackgroundColor(Color.parseColor("#153246"));
        btnFinalizado.setBackgroundColor(Color.parseColor("#153246"));
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getPedidosCliente(idUsuario).enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                int aux = 0;
                if (response.body()!=null) {
                    aux = response.body().size();
                }
                pedidos.clear();
                if (aux > 0) {
                    for (int i = 1; i <= aux; i++) {
                        pedido = new Pedido();
                        pedido.setId(response.body().get(i - 1).getId());
                        pedido.setId_Cliente(response.body().get(i - 1).getId_Cliente());
                        pedido.setId_Servico(response.body().get(i - 1).getId_Servico());
                        pedido.setId_Prestador(response.body().get(i - 1).getId_Prestador());
                        pedidos.add(pedido);
                    }
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                customAdapter = new CustomAdapterPedido(Pedidos.this, pedidos, Pedidos.this, parametro);
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Toast.makeText(Pedidos.this, "Sem Sucesso ao carregar lista de pedidos!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btnListaPedidoRecebidas(View view) {
        parametro = "RECEBIDAS";

        btnEnviadas.setBackgroundColor(Color.parseColor("#153246"));
        btnRecebidas.setBackgroundColor(Color.parseColor("#204c6a"));
        btnEmProgresso.setBackgroundColor(Color.parseColor("#153246"));
        btnFinalizado.setBackgroundColor(Color.parseColor("#153246"));
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getPedidosPrestador(idUsuario).enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                int aux = 0;
                if (response.body()!=null) {
                    aux = response.body().size();
                }
                pedidos.clear();
                if (aux > 0) {
                    for (int i = 1; i <= aux; i++) {
                        pedido = new Pedido();
                        pedido.setId(response.body().get(i - 1).getId());
                        pedido.setId_Cliente(response.body().get(i - 1).getId_Cliente());
                        pedido.setId_Servico(response.body().get(i - 1).getId_Servico());
                        pedido.setId_Prestador(response.body().get(i - 1).getId_Prestador());
                        pedidos.add(pedido);
                    }
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                customAdapter = new CustomAdapterPedido(Pedidos.this, pedidos, Pedidos.this, parametro);
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Toast.makeText(Pedidos.this, "Sem Sucesso ao carregar lista de pedidos!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btnListaPedidoEmProgresso(View view) {
        parametro = "PROGRESSO";

        btnEnviadas.setBackgroundColor(Color.parseColor("#153246"));
        btnRecebidas.setBackgroundColor(Color.parseColor("#153246"));
        btnEmProgresso.setBackgroundColor(Color.parseColor("#204c6a"));
        btnFinalizado.setBackgroundColor(Color.parseColor("#153246"));
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getPedidosEmProgresso(idUsuario).enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                int aux = 0;
                if (response.body()!=null) {
                    aux = response.body().size();
                }
                pedidos.clear();
                if (aux > 0) {
                    for (int i = 1; i <= aux; i++) {
                        pedido = new Pedido();
                        pedido.setId(response.body().get(i - 1).getId());
                        pedido.setId_Cliente(response.body().get(i - 1).getId_Cliente());
                        pedido.setId_Servico(response.body().get(i - 1).getId_Servico());
                        pedido.setId_Prestador(response.body().get(i - 1).getId_Prestador());
                        pedidos.add(pedido);
                    }
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                customAdapter = new CustomAdapterPedido(Pedidos.this, pedidos, Pedidos.this, parametro);
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Toast.makeText(Pedidos.this, "Sem Sucesso ao carregar lista de pedidos!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btnListaPedidoFinalizado(View view) {
        parametro = "FINALIZADO";

        btnEnviadas.setBackgroundColor(Color.parseColor("#153246"));
        btnRecebidas.setBackgroundColor(Color.parseColor("#153246"));
        btnEmProgresso.setBackgroundColor(Color.parseColor("#153246"));
        btnFinalizado.setBackgroundColor(Color.parseColor("#204c6a"));
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getPedidosFinalizados(idUsuario).enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                int aux = 0;
                if (response.body()!=null) {
                    aux = response.body().size();
                }
                pedidos.clear();
                if (aux > 0) {
                    for (int i = 1; i <= aux; i++) {
                        pedido = new Pedido();
                        pedido.setId(response.body().get(i - 1).getId());
                        pedido.setId_Cliente(response.body().get(i - 1).getId_Cliente());
                        pedido.setId_Servico(response.body().get(i - 1).getId_Servico());
                        pedido.setId_Prestador(response.body().get(i - 1).getId_Prestador());
                        pedidos.add(pedido);
                    }
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                customAdapter = new CustomAdapterPedido(Pedidos.this, pedidos, Pedidos.this, parametro);
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Toast.makeText(Pedidos.this, "Sem Sucesso ao carregar lista de pedidos!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void AceitaPedido(int position, Long id) {
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        pedido = new Pedido();
        pedido.setStatus("ACEITO");
        idPedido = id;
        api.updatePedido(id,pedido).enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                Toast.makeText(Pedidos.this, "Pedido Aceito!", Toast.LENGTH_SHORT).show();
                negociacaoDireta(idPedido);
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Toast.makeText(Pedidos.this, "Falha ao aceitar pedido!", Toast.LENGTH_SHORT).show();
            }
        });
        lista();
    }

    @Override
    public void RecusaPedido(int position, Long id) {
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        pedido = new Pedido();
        pedido.setStatus("RECUSADO");
        api.updatePedido(id,pedido).enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                Toast.makeText(Pedidos.this, "Pedido Recusado!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Toast.makeText(Pedidos.this, "Falha ao Recusar Pedido!", Toast.LENGTH_SHORT).show();
            }
        });
        lista();
    }

    @Override
    public void CancelaPedido(int position, Long id) {
        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        pedido = new Pedido();
        pedido.setStatus("CANCELADO");
        api.updatePedido(id,pedido).enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                Toast.makeText(Pedidos.this, "Pedido Cancelado!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Toast.makeText(Pedidos.this, "Falha ao Recusar Pedido!", Toast.LENGTH_SHORT).show();
            }
        });
        lista();
    }

    @Override
    public void VisualizarPedido(int position, Long id) {
        negociacaoDireta(idPedido);
    }

    @Override
    public void FinalizarPedido(int adapterPosition, Long id, Long idCliente, Long idPrestador) {
        dialog.setContentView(R.layout.z_custom_alertdialog_avaliacao);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        MaterialButton btnConfirma = dialog.findViewById(R.id.btnConfirmaAvaliacao);
        ImageView star_1 = dialog.findViewById(R.id.star_1_avaliacao);
        ImageView star_2 = dialog.findViewById(R.id.star_2_avaliacao);
        ImageView star_3 = dialog.findViewById(R.id.star_3_avaliacao);
        ImageView star_4 = dialog.findViewById(R.id.star_4_avaliacao);
        ImageView star_5 = dialog.findViewById(R.id.star_5_avaliacao);

        star_1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                star_1.setBackground(getDrawable(R.drawable.ic_star_gold));
                star_2.setBackground(getDrawable(R.drawable.ic_star));
                star_3.setBackground(getDrawable(R.drawable.ic_star));
                star_4.setBackground(getDrawable(R.drawable.ic_star));
                star_5.setBackground(getDrawable(R.drawable.ic_star));
            }
        });
        star_2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                star_1.setBackground(getDrawable(R.drawable.ic_star_gold));
                star_2.setBackground(getDrawable(R.drawable.ic_star_gold));
                star_3.setBackground(getDrawable(R.drawable.ic_star));
                star_4.setBackground(getDrawable(R.drawable.ic_star));
                star_5.setBackground(getDrawable(R.drawable.ic_star));
            }
        });
        star_3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                star_1.setBackground(getDrawable(R.drawable.ic_star_gold));
                star_2.setBackground(getDrawable(R.drawable.ic_star_gold));
                star_3.setBackground(getDrawable(R.drawable.ic_star_gold));
                star_4.setBackground(getDrawable(R.drawable.ic_star));
                star_5.setBackground(getDrawable(R.drawable.ic_star));
            }
        });
        star_4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                star_1.setBackground(getDrawable(R.drawable.ic_star_gold));
                star_2.setBackground(getDrawable(R.drawable.ic_star_gold));
                star_3.setBackground(getDrawable(R.drawable.ic_star_gold));
                star_4.setBackground(getDrawable(R.drawable.ic_star_gold));
                star_5.setBackground(getDrawable(R.drawable.ic_star));
            }
        });
        star_5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                star_1.setBackground(getDrawable(R.drawable.ic_star_gold));
                star_2.setBackground(getDrawable(R.drawable.ic_star_gold));
                star_3.setBackground(getDrawable(R.drawable.ic_star_gold));
                star_4.setBackground(getDrawable(R.drawable.ic_star_gold));
                star_5.setBackground(getDrawable(R.drawable.ic_star_gold));
            }
        });


        btnConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dialog.show();
    }

    public void btn_cards_to_main(View view) {
        Intent it = new Intent(this, MainActivity.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        it.putExtras(parametros);
        startActivity(it);
    }

    private void negociacaoDireta(Long idServico) {
        setContentView(R.layout.perfil_solicitacao);
        MaterialTextView ttvCliente = findViewById(R.id.ttvUsernamePerfilSolicita);
        MaterialTextView ttvClienteUF = findViewById(R.id.ttvCidadePerfilSolicita);
        MaterialTextView ttvClienteEmail = findViewById(R.id.ttvEmailPerfilSolicita);
        MaterialTextView ttvClienteTelefone = findViewById(R.id.ttvTelefonePerfilSolicita);
        MaterialTextView ttvClientesite = findViewById(R.id.ttvSitePerfilSolicita);
        MaterialTextView ttvClienteServicoNome = findViewById(R.id.ttvServicoPerfilSolicita);
        MaterialTextView ttvClienteServicoDesc = findViewById(R.id.ttvDescServicoPerfilSolicita);
        MaterialTextView ttvClienteServicoValor = findViewById(R.id.ttvValorServicoPerfilSolicita);
        MaterialButton btnSeguirNegociacao = findViewById(R.id.btnSegueNegociacaoPerfilSolicita);

        retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getPedidoById(idServico).enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if (response.body()!=null) {
                    ttvCliente.setText(response.body().getId_Cliente().getPrimeiroNome());
                    ttvClienteUF.setText(response.body().getId_Cliente().getUf());
                    ttvClienteEmail.setText(response.body().getId_Cliente().getEmail());
                    ttvClienteTelefone.setText(response.body().getId_Cliente().getTelefone());
                    ttvClientesite.setText(response.body().getId_Cliente().getSite());
                    ttvClienteServicoNome.setText(response.body().getId_Servico().getNome());
                    ttvClienteServicoDesc.setText(response.body().getId_Servico().getObsServico());
                    ttvClienteServicoValor.setText("Valor: R$"+response.body().getId_Servico().getValor().toString());
                    nomePrestador = response.body().getId_Prestador().getPrimeiroNome();
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {

            }
        });

        btnSeguirNegociacao.setOnClickListener(view -> {
            nomeCliente = ttvCliente.getText().toString();
            nomeCliente = nomeCliente.replace(" ", "%20");
            nomePrestador = nomePrestador.replace(" ", "%20");
            telefone = ttvClienteTelefone.getText().toString();
            telefone = telefone.replace(" ", "%20");
            servico = ttvClienteServicoNome.getText().toString();
            servico = servico.replace(" ", "%20");
            descservico = ttvClienteServicoDesc.getText().toString();
            descservico = descservico.replace(" ", "%20");
            valor = ttvClienteServicoValor.getText().toString();
            valor = valor.replace(" ", "%20");

            String url =
                    "https://api.whatsapp.com/send?phone=55"+telefone
                    +"&text=Ol%C3%A1%2C%20"+nomeCliente
                    +"%2C%20sou%20o%20"+nomePrestador
                    +"%2C%20Prestador%20de%20Servi%C3%A7o%20no%20aplicativo%20GO%20SERVICE.%0A%0A*Sua%20solicita%C3%A7%C3%A3o%20foi%20aceita.*"
                    +"%0A%0AServi%C3%A7o%3A%20"+servico+"."
                    +"%0ADescricao%3A%20"+descservico+"."
                    +"%0A"+valor+"."
                    +"%0A%0A*Vamos%20Marcar%20%3F*";

            retrofitService = new RetrofitService();
            api = retrofitService.getRetrofit().create(API.class);
            pedido = new Pedido();
            pedido.setStatus("ACEITO");
            api.updatePedido(idServico,pedido).enqueue(new Callback<Pedido>() {
                @Override
                public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                    Toast.makeText(Pedidos.this, "Pedido Aceito!", Toast.LENGTH_SHORT).show();
                    it = new Intent(Intent.ACTION_VIEW);
                    it.setData(Uri.parse(url));
                    startActivity(it);
                }

                @Override
                public void onFailure(Call<Pedido> call, Throwable t) {
                    Toast.makeText(Pedidos.this, "Falha ao Aceitar o Pedido!", Toast.LENGTH_SHORT).show();
                }
            });
            it = new Intent(this, MainActivity.class);
            Bundle parametros = new Bundle();
            parametros.putLong("id_usuario", idUsuario);
            it.putExtras(parametros);
            startActivity(it);
        });

    }
}
