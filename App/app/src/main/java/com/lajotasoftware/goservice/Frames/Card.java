package com.lajotasoftware.goservice.Frames;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.lajotasoftware.goservice.Adapter.CustomAdapterCard;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
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

public class Card extends AppCompatActivity implements CustomAdapterCard.OnCardListener {

    Long idUsuario, idPrestador, idCardServico;
    String status;
    API usuarioAPI;
    Intent it;
    Spinner categoria_servico, sub_categoria_servico;

    CustomAdapterCard customAdapter;
    RecyclerView recyclerView;
    List<SolicitaServico> cardsServicos = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it  = getIntent();
        Bundle parametros = it.getExtras();
        status = parametros.getString("status_usuario");
        idUsuario = parametros.getLong("id_usuario");
        if (status.equals("DEFAUT")) {
            setContentView(R.layout.cards_servico);
            initializeComponents();
        }if (status.equals("CRIAR_CARTAO")) {
            setContentView(R.layout.cadastro_servico);
            initializeComponentsCadastroCartao();
        }if (status.equals("EDITAR_CARTAO")) {
            setContentView(R.layout.cadastro_servico);
            initializeComponentsCadastroCartao();
        }
    }
    private void initializeComponents() {
        recyclerView = findViewById(R.id.listCardServico);
        listarCards();
    }

    private void listarCards() {
        RetrofitService retrofitServiceListService = new RetrofitService();
        API usuarioAPIListCardService = retrofitServiceListService.getRetrofit().create(API.class);
        usuarioAPIListCardService.getCardServico(idUsuario).enqueue(new Callback<List<SolicitaServico>>() {
            @Override
            public void onResponse(Call<List<SolicitaServico>> call, Response<List<SolicitaServico>> response) {
                int aux = response.body().size();
                cardsServicos.clear();
                for (int i=1; i<=aux;i++){
                    SolicitaServico solicitaServico = new SolicitaServico();
                    solicitaServico.setId(response.body().get(i-1).getId());
                    solicitaServico.setNomeServico(response.body().get(i-1).getNomeServico());
                    solicitaServico.setDescricaoSolicitacao(response.body().get(i-1).getDescricaoSolicitacao());
                    solicitaServico.setValor(response.body().get(i-1).getValor());
                    cardsServicos.add(solicitaServico);
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                customAdapter = new CustomAdapterCard(Card.this, cardsServicos, Card.this);
                recyclerView.setAdapter(customAdapter);
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
        setContentView(R.layout.cadastro_servico);
        initializeComponentsCadastroCartao();
    }
    @Override
    public void onCardRemoverClick(int position, Long id) {
        AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Excluir Card");
        alertDialogBuilder
            .setMessage("Clique sim para Exluir o Card!")
            .setCancelable(false)
            .setPositiveButton("Sim",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RetrofitService retrofitEditService = new RetrofitService();
                        API usuarioAPI = retrofitEditService.getRetrofit().create(API.class);
                        usuarioAPI.deleteCardServico(id).enqueue(new Callback<SolicitaServico>() {
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
    private void initializeComponentsCadastroCartao() {
        RetrofitService retrofitService = new RetrofitService();
        usuarioAPI = retrofitService.getRetrofit().create(API.class);
        Usuario usuario = new Usuario();
        Usuario user = new Usuario();
        usuario.setId(idUsuario);
        usuarioAPI.getAtualUser(usuario).enqueue(new Callback<Usuario>() {
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
            /*categoria_servico = (Spinner) findViewById(R.id.spinner_categoria);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categoria_servico, android.R.layout.simple_spinner_item);
            categoria_servico.setAdapter(adapter);

            sub_categoria_servico = (Spinner) findViewById(R.id.spinner_sub_categoria);
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sub_categoria_servico, android.R.layout.simple_spinner_item);
            sub_categoria_servico.setAdapter(adapter1);

            spinnerCategoriaServico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String catSelected = spinnerCategoriaServico.getSelectedItem().toString();
                    if (catSelected.equals(" ")) {
                        sub_categoria_servico = (Spinner) findViewById(R.id.spinner_sub_categoria);
                        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(Card.this, R.array.sub_categoria_servico, android.R.layout.simple_spinner_item);
                        spinnerSubCategoriaServico.setAdapter(adapter1);
                    } else if (catSelected.equals("Informática")) {
                        sub_categoria_servico = (Spinner) findViewById(R.id.spinner_sub_categoria);
                        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(Card.this, R.array.sub_categoria_servico_Informática, android.R.layout.simple_spinner_item);
                        spinnerSubCategoriaServico.setAdapter(adapter1);
                    } else if (catSelected.equals("Marcenaria")) {
                        sub_categoria_servico = (Spinner) findViewById(R.id.spinner_sub_categoria);
                        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(Card.this, R.array.sub_categoria_servico_Marcenaria, android.R.layout.simple_spinner_item);
                        spinnerSubCategoriaServico.setAdapter(adapter2);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    Toast.makeText(Card.this, "Nada Selecionado!", Toast.LENGTH_SHORT).show();
                }
            });*/
        }
        if (status.equals("EDITAR_CARTAO")) {
            /*RetrofitService retrofitEditService = new RetrofitService();
            usuarioAPI = retrofitEditService.getRetrofit().create(API.class);
            usuarioAPI.getCardServicoById(idCardServico).enqueue(new Callback<SolicitaServico>() {
                @Override
                public void onResponse(Call<SolicitaServico> call, Response<SolicitaServico> servResponse) {
                    if (servResponse.isSuccessful()) {
                        SolicitaServico cardServ = new SolicitaServico();
                        assert servResponse.body() != null;
                        cardServ.setServico(servResponse.body());
                        idCardServico = cardServ.getId();
                        inputEditTextNomeServico.setText(cardServ.getNomeServico());
                        inputEditTextDescricaoServico.setText(cardServ.getDescricaoSolicitacao());
                        inputEditTextValorServico.setText(cardServ.getValor().toString());
                        ArrayAdapter<CharSequence> adapterCategoria = ArrayAdapter.createFromResource(Card.this, R.array.categoria_servico, android.R.layout.simple_spinner_item);
                        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCategoriaServico.setAdapter(adapterCategoria);
                        if (!cardServ.getCategoria().equals(null)) {
                            int spinnerPosition = adapterCategoria.getPosition(cardServ.getCategoria());
                            spinnerCategoriaServico.setSelection(spinnerPosition);
                        }
                        if (cardServ.getCategoria().equals("Informática")) {
                            ArrayAdapter<CharSequence> adapterSubCategoria = ArrayAdapter.createFromResource(Card.this, R.array.sub_categoria_servico_Informática, android.R.layout.simple_spinner_item);
                            adapterSubCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerSubCategoriaServico.setAdapter(adapterSubCategoria);
                            if (!cardServ.getSubCategoria().equals(null)) {
                                int spinnerPosition = adapterSubCategoria.getPosition(cardServ.getSubCategoria());
                                spinnerSubCategoriaServico.setSelection(spinnerPosition);
                            }
                        }
                        if (cardServ.getCategoria().equals("Marcenaria")) {
                            ArrayAdapter<CharSequence> adapterSubCategoria = ArrayAdapter.createFromResource(Card.this, R.array.sub_categoria_servico_Marcenaria, android.R.layout.simple_spinner_item);
                            adapterSubCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerSubCategoriaServico.setAdapter(adapterSubCategoria);
                            if (!cardServ.getSubCategoria().equals(null)) {
                                int spinnerPosition = adapterSubCategoria.getPosition(cardServ.getSubCategoria());
                                spinnerSubCategoriaServico.setSelection(spinnerPosition);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<SolicitaServico> call, Throwable t) {
                    Toast.makeText(Card.this, "Falha ao editar! \n Tente novamente. \n Se o problema persistir contate o suporte", Toast.LENGTH_SHORT).show();
                }
            });*/
        }

        btn_gravar_cardservico.setOnClickListener(view -> {
            Double valorServico = null;
            String catServico = null, subcatServico = null;
            String nomeServico = String.valueOf(inputEditTextNomeServico.getText());
            if ((inputEditTextValorServico.getText()!=null) && (!inputEditTextValorServico.getText().equals(""))){
                valorServico = Double.parseDouble(inputEditTextValorServico.getText().toString());
            }
            String descServico = String.valueOf(inputEditTextDescricaoServico.getText());
            if (spinnerCategoriaServico.getSelectedItem().toString() != null) {
                catServico = spinnerCategoriaServico.getSelectedItem().toString();
            }
            if (spinnerSubCategoriaServico.getSelectedItem().toString() != null) {
                subcatServico = spinnerSubCategoriaServico.getSelectedItem().toString();
            }
            if (!nomeServico.equals("") && nomeServico.length() >= 5) {
                if (valorServico > 0 && valorServico < 100000 && valorServico!=null) {
                    if (!descServico.equals("") && descServico.length() > 15) {
                        if (!catServico.equals("") && !catServico.equals(" ") && catServico != null) {
                            if (!subcatServico.equals("") && !subcatServico.equals(" ") && subcatServico != null) {
                                SolicitaServico cardServico = new SolicitaServico();
                                cardServico.setValor(valorServico);
                                cardServico.setValorProposto(0.0);
                                if (status.equals("EDITAR_CARTAO")){
                                    //cardServico.setValorProposto();
                                }
                                cardServico.setNomeServico(nomeServico);
                                cardServico.setDescricaoSolicitacao(descServico);
                                cardServico.setCategoria(catServico);
                                cardServico.setSubCategoria(subcatServico);
                                cardServico.setId_Cliente(user);

                                it = new Intent(this, MainActivity.class);
                                Bundle parametros = new Bundle();
                                parametros.putLong("id_usuario", idUsuario);
                                it.putExtras(parametros);

                                if (status.equals("EDITAR_CARTAO")) {
                                    usuarioAPI.updateCardServico(idCardServico, cardServico).enqueue(new Callback<SolicitaServico>() {
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
                                    usuarioAPI.createNewCardService(cardServico).enqueue(new Callback<SolicitaServico>() {
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
                                Toast.makeText(Card.this, "Sub-Categoria não selecionada!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Card.this, "Categoria não selecionada!", Toast.LENGTH_SHORT).show();
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

    public void createDialog(View view,int position){
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("O que deseja fazer?");
        /*adb.setPositiveButton("Finalizar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Servico finalizado", Toast.LENGTH_LONG).show();
                String sv = servicos.get(position);
                finalizarServico(sv);
                //databaseReference.child("CadServico").child(sv.getId()).removeValue();
            } });*/
        adb.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Editar", Toast.LENGTH_LONG).show();
                //String cardSv = cardsServicos.get(position);
                //editar(cardSv);
            } });
        adb.setNeutralButton("Excluir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //String cardSv = cardsServicos.get(position);
                //excluir(cardSv);
            } });
        AlertDialog alertDialog = adb.create();
        alertDialog.show();
    }

    public void btn_card_to_createcard (View view) {
        Intent it = new Intent(this, Card.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        parametros.putString("status_usuario", "CRIAR_CARTAO");
        it.putExtras(parametros);
        startActivity(it);
    }

    public void btn_cards_to_main (View view){
        Intent it = new Intent(this, MainActivity.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        it.putExtras(parametros);
        startActivity(it);
    }



}
