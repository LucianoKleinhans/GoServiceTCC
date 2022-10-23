package com.lajotasoftware.goservice.Frames;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.lajotasoftware.goservice.Adapter.CustomAdapterCard;
import com.lajotasoftware.goservice.Adapter.CustomAdapterProposta;
import com.lajotasoftware.goservice.Entity.Categoria;
import com.lajotasoftware.goservice.Entity.Proposta;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Entity.SubCategoria;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.MainActivity;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.RetrofitService;
import com.lajotasoftware.goservice.retrofit.API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Card extends AppCompatActivity implements CustomAdapterCard.OnCardListener, CustomAdapterProposta.OnPropostaListener {

    Long idUsuario, idPrestador, idCardServico, idCategoria, idSubCategoria, idPropostaAceita;
    String status, parametro;
    int auxiliar = 0;
    Boolean prestadorTF;

    API api;
    Categoria categoria;
    SubCategoria subCategoria;
    Proposta proposta;

    List<Categoria> categorias = new ArrayList<>();
    List<SubCategoria> subCategorias = new ArrayList<>();
    List<SolicitaServico> cardsServicos = new ArrayList<>();
    List<Proposta> propostas = new ArrayList<>();

    Intent it;
    Dialog dialog;
    Spinner categoria_servico, sub_categoria_servico;

    CustomAdapterCard customAdapterCard;
    CustomAdapterProposta customAdapterProposta;
    RecyclerView recyclerView;
    RecyclerView recyclerViewProposta;
    MaterialButton btnMeusCards;
    MaterialButton btnCardsPublicos;
    MaterialButton btnPropostasEnviadas;
    MaterialButton btnCardsFinalizadas;
    ProgressBar progressBarCards;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        Bundle parametros = it.getExtras();
        status = parametros.getString("status_usuario");
        idUsuario = parametros.getLong("id_usuario");
        if (status.equals("DEFAUT")) {
            setContentView(R.layout.cards_servico);
            prestadorTF = parametros.getBoolean("pretador");
            initializeComponents();
        }
        if (status.equals("CRIAR_CARTAO")) {
            setContentView(R.layout.cadastro_servico);
            initializeComponentsCadastroCartao();
        }
        if (status.equals("EDITAR_CARTAO")) {
            setContentView(R.layout.cadastro_servico);
            initializeComponentsCadastroCartao();
        }
    }

    private void initializeComponents() {
        dialog = new Dialog(this);
        recyclerView = findViewById(R.id.listCardServico);
        btnMeusCards = findViewById(R.id.btnMeusCards);
        btnCardsPublicos = findViewById(R.id.btnCardsPublicos);
        btnPropostasEnviadas = findViewById(R.id.btnPropostasEnviadas);
        btnCardsFinalizadas = findViewById(R.id.btnCardsFinalizadas);
        progressBarCards = findViewById(R.id.progressBarCards);
        listarCards();
    }

    private void listarCards() {
        parametro = "MEUS_CARDS";
        btnMeusCards.setBackgroundColor(Color.parseColor("#204c6a"));
        btnCardsPublicos.setBackgroundColor(Color.parseColor("#153246"));
        btnPropostasEnviadas.setBackgroundColor(Color.parseColor("#153246"));
        btnCardsFinalizadas.setBackgroundColor(Color.parseColor("#153246"));
        progressBarCards.setVisibility(View.VISIBLE);
        RetrofitService retrofitServiceListService = new RetrofitService();
        api = retrofitServiceListService.getRetrofit().create(API.class);
        api.getCardServico(idUsuario).enqueue(new Callback<List<SolicitaServico>>() {
            @Override
            public void onResponse(Call<List<SolicitaServico>> call, Response<List<SolicitaServico>> response) {
                progressBarCards.setVisibility(View.GONE);
                int aux = 0;
                if (response.body() != null) {
                    aux = response.body().size();
                }
                cardsServicos.clear();
                for (int i = 1; i <= aux; i++) {
                    SolicitaServico solicitaServico = new SolicitaServico();
                    solicitaServico.setServico(response.body().get(i - 1));
                    cardsServicos.add(solicitaServico);
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                customAdapterCard = new CustomAdapterCard(Card.this, cardsServicos, Card.this, parametro);
                recyclerView.setAdapter(customAdapterCard);
            }

            @Override
            public void onFailure(Call<List<SolicitaServico>> call, Throwable t) {
                Toast.makeText(Card.this, "Sem Sucesso ao carregar lista de serviço!", Toast.LENGTH_SHORT).show();
                progressBarCards.setVisibility(View.GONE);
                onBackPressed();
            }
        });
    }

    public void btnMeusCards(View view) {
        listarCards();
    }

    public void btnCardsPublicos(View view) {
        parametro = "CARDS_PUBLICOS";
        btnMeusCards.setBackgroundColor(Color.parseColor("#153246"));
        btnCardsPublicos.setBackgroundColor(Color.parseColor("#204c6a"));
        btnPropostasEnviadas.setBackgroundColor(Color.parseColor("#153246"));
        btnCardsFinalizadas.setBackgroundColor(Color.parseColor("#153246"));
        if (prestadorTF) {
            RetrofitService retrofitServiceListService = new RetrofitService();
            api = retrofitServiceListService.getRetrofit().create(API.class);
            api.getCardsServicoPublico(idUsuario).enqueue(new Callback<List<SolicitaServico>>() {
                @Override
                public void onResponse(Call<List<SolicitaServico>> call, Response<List<SolicitaServico>> response) {
                    int aux = 0;
                    if (response.body() != null) {
                        aux = response.body().size();
                    }
                    cardsServicos.clear();
                    for (int i = 1; i <= aux; i++) {
                        SolicitaServico solicitaServico = new SolicitaServico();
                        solicitaServico.setServico(response.body().get(i - 1));
                        cardsServicos.add(solicitaServico);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    customAdapterCard = new CustomAdapterCard(Card.this, cardsServicos, Card.this, parametro);
                    recyclerView.setAdapter(customAdapterCard);
                }

                @Override
                public void onFailure(Call<List<SolicitaServico>> call, Throwable t) {
                    Toast.makeText(Card.this, "Sem Sucesso ao carregar lista de serviço!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            propostas.clear();
            cardsServicos.clear();
            recyclerView.setAdapter(null);
            Toast.makeText(Card.this, "É necessário se tornar um prestador para usar essa função!", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnPropostasEnviadas(View view) {
        parametro = "CARDS_PROPOSTAS_ENVIADAS";
        btnMeusCards.setBackgroundColor(Color.parseColor("#153246"));
        btnCardsPublicos.setBackgroundColor(Color.parseColor("#153246"));
        btnPropostasEnviadas.setBackgroundColor(Color.parseColor("#204c6a"));
        btnCardsFinalizadas.setBackgroundColor(Color.parseColor("#153246"));
        if (prestadorTF){
            RetrofitService retrofitServiceListService = new RetrofitService();
            api = retrofitServiceListService.getRetrofit().create(API.class);
            api.getPropostasEnviadas(idUsuario).enqueue(new Callback<List<Proposta>>() {
                @Override
                public void onResponse(Call<List<Proposta>> call, Response<List<Proposta>> response) {
                    int aux = 0;
                    if (response.body() != null) {
                        aux = response.body().size();
                    }
                    propostas.clear();
                    cardsServicos.clear();
                    for (int i = 1; i <= aux; i++) {
                        SolicitaServico solicitaServico = new SolicitaServico();
                        solicitaServico.setServico(response.body().get(i - 1).getId_SolicitaServico());
                        cardsServicos.add(solicitaServico);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    customAdapterCard = new CustomAdapterCard(Card.this, cardsServicos, Card.this, parametro);
                    recyclerView.setAdapter(customAdapterCard);
                }

                @Override
                public void onFailure(Call<List<Proposta>> call, Throwable t) {

                }
            });
        } else {
            propostas.clear();
            cardsServicos.clear();
            recyclerView.setAdapter(null);
            Toast.makeText(Card.this, "É necessário se tornar um prestador para usar essa função!", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnCardsFinalizadas(View view) {
        parametro = "CARDS_PROPOSTAS_FINALIZADAS";
        btnMeusCards.setBackgroundColor(Color.parseColor("#153246"));
        btnCardsPublicos.setBackgroundColor(Color.parseColor("#153246"));
        btnPropostasEnviadas.setBackgroundColor(Color.parseColor("#153246"));
        btnCardsFinalizadas.setBackgroundColor(Color.parseColor("#204c6a"));
        RetrofitService retrofitServiceListService = new RetrofitService();
        api = retrofitServiceListService.getRetrofit().create(API.class);
        api.getCardsServicoFinalizados(idUsuario).enqueue(new Callback<List<SolicitaServico>>() {
            @Override
            public void onResponse(Call<List<SolicitaServico>> call, Response<List<SolicitaServico>> response) {
                int aux = 0;
                if (response.body() != null) {
                    aux = response.body().size();
                }
                cardsServicos.clear();
                for (int i = 1; i <= aux; i++) {
                    SolicitaServico solicitaServico = new SolicitaServico();
                    solicitaServico.setServico(response.body().get(i - 1));
                    cardsServicos.add(solicitaServico);
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                customAdapterCard = new CustomAdapterCard(Card.this, cardsServicos, Card.this, parametro);
                recyclerView.setAdapter(customAdapterCard);
            }

            @Override
            public void onFailure(Call<List<SolicitaServico>> call, Throwable t) {
                Toast.makeText(Card.this, "Sem Sucesso ao carregar lista de serviço!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCardVisualizarClick(int position, Long id) {
        idCardServico = id;
        status = "VISUALIZAR_CARTAO";
        setContentView(R.layout.card_servico_visualizacao);
        MaterialTextView ttvidCardServico = findViewById(R.id.idCardServico);
        MaterialTextView ttvnomeCardServico = findViewById(R.id.ttvNomeCardServico);
        MaterialTextView ttvdescCardServico = findViewById(R.id.ttvDescCardServico);
        MaterialTextView ttvvalorInicialCardServico = findViewById(R.id.ttvValorCardServico);
        MaterialTextView ttvvalorAtualCardServico = findViewById(R.id.ttvCardValorAtual);
        recyclerViewProposta = findViewById(R.id.recyclerViewProposta);
        RetrofitService retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getCardServicoById(id).enqueue(new Callback<SolicitaServico>() {
            @Override
            public void onResponse(Call<SolicitaServico> call, Response<SolicitaServico> response) {
                if (response.body() != null) {
                    idCardServico = response.body().getId();
                    ttvidCardServico.setText(response.body().getId().toString());
                    ttvnomeCardServico.setText(response.body().getNomeServico());
                    ttvdescCardServico.setText(response.body().getDescricaoSolicitacao());
                    ttvvalorInicialCardServico.setText("Valor inicial R$"+response.body().getValor().toString());
                    if (response.body().getValorProposto() == 0){
                        ttvvalorAtualCardServico.setText("Sem Propostas");
                    }else{
                        ttvvalorAtualCardServico.setText("Valor atual R$"+response.body().getValorProposto().toString());
                    }
                    listarPropostas(idCardServico);
                }else{
                    onBackPressed();
                    Toast.makeText(Card.this, "Erro ao carregar Card!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SolicitaServico> call, Throwable t) {

            }
        });
    }

    private void listarPropostas(Long idCardServico) {
        RetrofitService retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getPropostasRecebidas(idCardServico).enqueue(new Callback<List<Proposta>>() {
            @Override
            public void onResponse(Call<List<Proposta>> call, Response<List<Proposta>> response) {
                int aux = 0;
                if (response.body() != null) {
                    aux = response.body().size();
                }
                if (aux > 0) {
                    propostas.clear();
                    for (int i = 1; i <= aux; i++) {
                        proposta = new Proposta();
                        proposta.setProposta(response.body().get(i - 1));
                        propostas.add(proposta);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerViewProposta.setLayoutManager(linearLayoutManager);
                    customAdapterProposta = new CustomAdapterProposta(Card.this, propostas, Card.this);
                    recyclerViewProposta.setAdapter(customAdapterProposta);
                }
            }

            @Override
            public void onFailure(Call<List<Proposta>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCardRemoverClick(int position, Long id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Excluir Card");
        alertDialogBuilder
                .setMessage("Clique sim para Exluir o Card!")
                .setCancelable(false)
                .setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SolicitaServico card = new SolicitaServico();
                                card.setExcluido(true);
                                RetrofitService retrofitEditService = new RetrofitService();
                                API usuarioAPI = retrofitEditService.getRetrofit().create(API.class);
                                usuarioAPI.updateCardServico(id, card).enqueue(new Callback<SolicitaServico>() {
                                    @Override
                                    public void onResponse(Call<SolicitaServico> call, Response<SolicitaServico> response) {
                                        Toast.makeText(Card.this, "Card de serviço excluído com sucesso!", Toast.LENGTH_SHORT).show();
                                        listarCards();
                                    }

                                    @Override
                                    public void onFailure(Call<SolicitaServico> call, Throwable t) {
                                        Toast.makeText(Card.this, "Card de serviço excluído com sucesso!", Toast.LENGTH_SHORT).show();
                                        listarCards();
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

    @Override
    public void onCardEditarClick(int position, Long id) {
        idCardServico = id;
        status = "EDITAR_CARTAO";
        setContentView(R.layout.cadastro_servico);
        initializeComponentsCadastroCartao();
    }

    @Override
    public void onCardFazerPropostaClick(int position, Long id, Long idCliente) {
        dialog.setContentView(R.layout.z_custom_alertdialog_proposta);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        MaterialButton btnConfirma = dialog.findViewById(R.id.btnConfirmaProposta);
        TextInputEditText edtDescProposta = dialog.findViewById(R.id.edtDescricaoProposta);
        TextInputEditText edtValorProposta = dialog.findViewById(R.id.edtValorProposta);

        idPrestador = idUsuario;

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
                        card.setId(id);

                        proposta.setId_Prestador(prestador);
                        proposta.setId_Cliente(cliente);
                        proposta.setId_SolicitaServico(card);
                        proposta.setObservacao(edtDescProposta.getText().toString());
                        proposta.setValor(Double.valueOf(edtValorProposta.getText().toString()));
                        proposta.setStatus("ABERTO");

                        RetrofitService retrofitService = new RetrofitService();
                        retrofitService.getRetrofit().create(API.class);
                        api.criarProposta(proposta).enqueue(new Callback<Proposta>() {
                            @Override
                            public void onResponse(Call<Proposta> call, Response<Proposta> response) {
                                Toast.makeText(Card.this, "Proposta Criada com Sucesso!", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }

                            @Override
                            public void onFailure(Call<Proposta> call, Throwable t) {

                            }
                        });
                    } else {
                        Toast.makeText(Card.this, "Valor da proposta é obrigatório!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Card.this, "Descrição da proposta é obrigatória!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onCardVizualizarPropostaClick(int position, Long id, Long idCliente) {
        Intent it = new Intent(Card.this, Negociacao.class);
        Bundle parametros = new Bundle();
        RetrofitService retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        api.getCardServicoById(id).enqueue(new Callback<SolicitaServico>() {
            @Override
            public void onResponse(Call<SolicitaServico> call, Response<SolicitaServico> response) {
                RetrofitService retrofitService = new RetrofitService();
                api = retrofitService.getRetrofit().create(API.class);
                api.getPropostaSolicitacaoServico(response.body().getId(),idUsuario).enqueue(new Callback<Proposta>() {
                    @Override
                    public void onResponse(Call<Proposta> call, Response<Proposta> response) {
                        assert response.body() != null;
                        parametros.putLong("id_usuario", idUsuario);
                        parametros.putLong("id_proposta", response.body().getId());
                        parametros.putLong("id_prestador", idUsuario);
                        parametros.putLong("id_cliente", response.body().getId_Cliente().getId());
                        parametros.putLong("id_card_servico", id);
                        it.putExtras(parametros);
                        startActivity(it);
                    }

                    @Override
                    public void onFailure(Call<Proposta> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<SolicitaServico> call, Throwable t) {

            }
        });
    }

    @SuppressLint("CutPasteId")
    private void initializeComponentsCadastroCartao() {
        RetrofitService retrofitService = new RetrofitService();
        api = retrofitService.getRetrofit().create(API.class);
        Usuario usuario = new Usuario();
        Usuario user = new Usuario();
        usuario.setId(idUsuario);
        api.getAtualUser(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> usuarioResponse) {
                if (usuarioResponse.isSuccessful()) {
                    assert usuarioResponse.body() != null;
                    user.setUsuario(usuarioResponse.body());
                    idPrestador = user.getId_Prestador();
                    idUsuario = user.getId();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                throw new Error("USUARIO INVALIDO");
            }
        });

        TextInputEditText inputEditTextNomeServico = findViewById(R.id.edtNomeServico);
        EditText inputEditTextValorServico = findViewById(R.id.edtValorServico);
        TextInputEditText inputEditTextDescricaoServico = findViewById(R.id.edtDescricaoServico);

        Spinner spinnerCategoriaServico = findViewById(R.id.spinner_categoria);
        Spinner spinnerSubCategoriaServico = findViewById(R.id.spinner_sub_categoria);

        MaterialButton btn_gravar_cardservico = findViewById(R.id.btnGravarCadServico);
        MaterialButton btn_cancelar_cardservico = findViewById(R.id.btnCancelarCadServico);

        if (status.equals("CRIAR_CARTAO")) {
            categoria_servico = (Spinner) findViewById(R.id.spinner_categoria);
            sub_categoria_servico = (Spinner) findViewById(R.id.spinner_sub_categoria);
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
                            categoria.setId(response.body().get(i - 1).getId());
                            categoria.setNome(response.body().get(i - 1).getNome());
                            categorias.add(categoria);
                        }
                        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(getApplicationContext(), android.R.layout.simple_spinner_item, categorias);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        categoria_servico.setAdapter(adapter);
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
                                    subCategoria.setId(response.body().get(i - 1).getId());
                                    subCategoria.setNome(response.body().get(i - 1).getNome());
                                    subCategoria.setIdCategoriaServico(response.body().get(i - 1).getIdCategoriaServico());
                                    subCategorias.add(subCategoria);
                                }
                                ArrayAdapter<SubCategoria> adapter = new ArrayAdapter<SubCategoria>(getApplicationContext(), android.R.layout.simple_spinner_item, subCategorias);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sub_categoria_servico.setAdapter(adapter);
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
        }
        if (status.equals("EDITAR_CARTAO")) {
            categoria_servico = (Spinner) findViewById(R.id.spinner_categoria);
            sub_categoria_servico = (Spinner) findViewById(R.id.spinner_sub_categoria);
            RetrofitService retrofitEditService = new RetrofitService();
            api = retrofitEditService.getRetrofit().create(API.class);
            api.getCardServicoById(idCardServico).enqueue(new Callback<SolicitaServico>() {
                @Override
                public void onResponse(Call<SolicitaServico> call, Response<SolicitaServico> servResponse) {
                    if (servResponse.isSuccessful()) {
                        SolicitaServico card = new SolicitaServico();
                        assert servResponse.body() != null;
                        card.setServico(servResponse.body());
                        idCardServico = card.getId();
                        inputEditTextNomeServico.setText(card.getNomeServico());
                        inputEditTextDescricaoServico.setText(card.getDescricaoSolicitacao());
                        inputEditTextValorServico.setText(card.getValor().toString());

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
                                        categoria.setId(response.body().get(i - 1).getId());
                                        categoria.setNome(response.body().get(i - 1).getNome());
                                        categorias.add(categoria);
                                    }
                                    ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(getApplicationContext(), android.R.layout.simple_spinner_item, categorias);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    categoria_servico.setAdapter(adapter);
                                    if (card.getId_Categoria() != null) {
                                        spinnerCategoriaServico.setSelection(getIndex(spinnerCategoriaServico, card.getId_Categoria().toString()));
                                        idCategoria = card.getId_Categoria().getId();

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
                                                        subCategoria.setId(response.body().get(i - 1).getId());
                                                        subCategoria.setNome(response.body().get(i - 1).getNome());
                                                        subCategoria.setIdCategoriaServico(response.body().get(i - 1).getIdCategoriaServico());
                                                        subCategorias.add(subCategoria);
                                                    }
                                                    ArrayAdapter<SubCategoria> adapter = new ArrayAdapter<SubCategoria>(getApplicationContext(), android.R.layout.simple_spinner_item, subCategorias);
                                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    sub_categoria_servico.setAdapter(adapter);
                                                    if (card.getId_SubCategoria() != null) {
                                                        spinnerSubCategoriaServico.setSelection(getIndex(spinnerSubCategoriaServico, card.getId_SubCategoria().toString()));
                                                        idSubCategoria = card.getId_SubCategoria().getId();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<SubCategoria>> call, Throwable t) {

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Categoria>> call, Throwable t) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<SolicitaServico> call, Throwable t) {
                    Toast.makeText(Card.this, "Falha ao editar! \n Tente novamente. \n Se o problema persistir contate o suporte", Toast.LENGTH_SHORT).show();
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
                                    subCategoria.setId(response.body().get(i - 1).getId());
                                    subCategoria.setNome(response.body().get(i - 1).getNome());
                                    subCategoria.setIdCategoriaServico(response.body().get(i - 1).getIdCategoriaServico());
                                    subCategorias.add(subCategoria);
                                }
                                if (auxiliar > 0) {
                                    ArrayAdapter<SubCategoria> adapter = new ArrayAdapter<SubCategoria>(getApplicationContext(), android.R.layout.simple_spinner_item, subCategorias);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    sub_categoria_servico.setAdapter(adapter);
                                }
                                auxiliar++;
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
        }
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
        btn_gravar_cardservico.setOnClickListener(view -> {
            Double valorServico = null;
            String nomeServico = String.valueOf(inputEditTextNomeServico.getText());
            if ((inputEditTextValorServico.getText() != null) && (!inputEditTextValorServico.getText().equals(""))) {
                valorServico = Double.parseDouble(inputEditTextValorServico.getText().toString());
            }
            String descServico = String.valueOf(inputEditTextDescricaoServico.getText());
            if (!nomeServico.equals("") && nomeServico.length() >= 5) {
                if (valorServico > 0 && valorServico < 100000 && valorServico != null) {
                    if (!descServico.equals("") && descServico.length() > 15) {
                        SolicitaServico cardServico = new SolicitaServico();
                        categoria = new Categoria();
                        subCategoria = new SubCategoria();
                        categoria.setId(idCategoria);
                        subCategoria.setId(idSubCategoria);
                        cardServico.setValor(valorServico);
                        cardServico.setValorProposto(0.0);
                        if (status.equals("EDITAR_CARTAO")) {
                            //cardServico.setValorProposto();
                        }
                        cardServico.setNomeServico(nomeServico);
                        cardServico.setDescricaoSolicitacao(descServico);
                        cardServico.setId_Categoria(categoria);
                        cardServico.setId_SubCategoria(subCategoria);
                        cardServico.setId_Cliente(user);
                        cardServico.setStatus("ABERTO");
                        cardServico.setExcluido(false);

                        it = new Intent(this, MainActivity.class);
                        Bundle parametros = new Bundle();
                        parametros.putLong("id_usuario", idUsuario);
                        it.putExtras(parametros);

                        if (status.equals("EDITAR_CARTAO")) {
                            api.updateCardServico(idCardServico, cardServico).enqueue(new Callback<SolicitaServico>() {
                                @Override
                                public void onResponse(Call<SolicitaServico> call, Response<SolicitaServico> response) {
                                    if (response.code() == 200) {
                                        Toast.makeText(Card.this, "Serviço atualizado!", Toast.LENGTH_SHORT).show();
                                        startActivity(it);
                                    } else {
                                        Toast.makeText(Card.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SolicitaServico> call, Throwable t) {
                                    Toast.makeText(Card.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (status.equals("CRIAR_CARTAO")) {
                            api.createNewCardService(cardServico).enqueue(new Callback<SolicitaServico>() {
                                @Override
                                public void onResponse(Call<SolicitaServico> call, Response<SolicitaServico> response) {
                                    if (response.code() == 200) {
                                        Toast.makeText(Card.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                        startActivity(it);
                                    } else {
                                        Toast.makeText(Card.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SolicitaServico> call, Throwable t) {
                                    Toast.makeText(Card.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(Card.this, "Observação vazio!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Card.this, "Valor vazio!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Card.this, "Nome invalido!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_cancelar_cardservico.setOnClickListener(view -> {
            onBackPressed();
            status = "DEFAUT";
        });
    }

    public void btn_card_to_createcard(View view) {
        Intent it = new Intent(this, Card.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        parametros.putString("status_usuario", "CRIAR_CARTAO");
        it.putExtras(parametros);
        startActivity(it);
    }

    public void btn_cards_to_main(View view) {
        Intent it = new Intent(this, MainActivity.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        it.putExtras(parametros);
        startActivity(it);
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onPropostaNegociarClick(int adapterPosition, Long id, Long idCliente, Long idPrestador) {
        Intent it = new Intent(Card.this, Negociacao.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        parametros.putLong("id_proposta", id);
        parametros.putLong("id_prestador", idPrestador);
        parametros.putLong("id_cliente", idCliente);
        parametros.putLong("id_card_servico", idCardServico);
        it.putExtras(parametros);
        startActivity(it);
    }

    @Override
    public void onPropostaAceitarClick(int adapterPosition, Long id, Long idCliente, Long idPrest) {
        proposta = new Proposta();
        proposta.setStatus("ACEITO");
        idPropostaAceita = id;
        idPrestador = idPrest;
        RetrofitService retrofitService = new RetrofitService();
        retrofitService.getRetrofit().create(API.class);
        api.updateProposta(id, proposta).enqueue(new Callback<Proposta>() {
            @Override
            public void onResponse(Call<Proposta> call, Response<Proposta> response) {
                Toast.makeText(Card.this, "Proposta Aceita!", Toast.LENGTH_SHORT).show();
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
                            propostas.clear();
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
                                    Intent it = new Intent(Card.this, Pedidos.class);
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
                                    Intent it = new Intent(Card.this, Pedidos.class);
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
    }

    @Override
    public void onPropostaRecusarClick(int adapterPosition, Long id, Long idCliente, Long idPrestador) {
        proposta = new Proposta();
        proposta.setStatus("RECUSADO");
        RetrofitService retrofitService = new RetrofitService();
        retrofitService.getRetrofit().create(API.class);
        api.updateProposta(id, proposta).enqueue(new Callback<Proposta>() {
            @Override
            public void onResponse(Call<Proposta> call, Response<Proposta> response) {
                Toast.makeText(Card.this, "Proposta recusada!", Toast.LENGTH_SHORT).show();
                listarPropostas(idCardServico);
            }

            @Override
            public void onFailure(Call<Proposta> call, Throwable t) {

            }
        });
    }
}
