package com.lajotasoftware.goservice;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.lajotasoftware.goservice.Entity.Return;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.Frames.Card;
import com.lajotasoftware.goservice.Frames.Pedidos;
import com.lajotasoftware.goservice.Frames.Prestadores;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lajotasoftware.goservice.Frames.Perfil;
import com.lajotasoftware.goservice.Frames.Servicos;
import com.lajotasoftware.goservice.retrofit.RetrofitService;
import com.lajotasoftware.goservice.retrofit.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    Usuario user = new Usuario();
    long idUsuario;
    String username, status, codConfirmacao, novaSenha, novaSenhaConfirm, novaSenhaCodConfirmacao;
    ProgressBar progressBar;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        Bundle parametros = it.getExtras();
        idUsuario = parametros.getLong("id_usuario");
        status = parametros.getString("status");
        setContentView(R.layout.activity_main);
        initializeComponents();
    }

    private void initializeComponents() {
        MaterialTextView textViewUserName = findViewById(R.id.ttvUsernamePerfilUser2);
        MaterialButton btnGerenciaCardsMain = findViewById(R.id.btnGerenciaCardsMain);
        MaterialButton btnPrestadoresMain = findViewById(R.id.btnPrestadoresMain);
        MaterialButton btnServicosMain = findViewById(R.id.btnServicosMain);
        MaterialButton btnSolicitacoesMain = findViewById(R.id.btnSolicitacoesMain);
        progressBar = findViewById(R.id.progressBarMain);
        progressBar.setVisibility(View.VISIBLE);
        btnGerenciaCardsMain.setEnabled(false);
        btnPrestadoresMain.setEnabled(false);
        btnServicosMain.setEnabled(false);
        btnSolicitacoesMain.setEnabled(false);

        RetrofitService retrofitService = new RetrofitService();
        API usuarioAPI = retrofitService.getRetrofit().create(API.class);
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        usuarioAPI.getAtualUser(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> usuarioResponse) {
                if (usuarioResponse.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    btnGerenciaCardsMain.setEnabled(true);
                    btnPrestadoresMain.setEnabled(true);
                    btnServicosMain.setEnabled(true);
                    btnSolicitacoesMain.setEnabled(true);
                    assert usuarioResponse.body() != null;
                    user.setUsuario(usuarioResponse.body());

                    username = user.getLogin();
                    textViewUserName.setText(username);
                    if (status != null) {
                        if (status.equals("RECUPERACAO")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setTitle("Atenção");
                            dialog.setMessage("Houve uma tentativa de recuperação de senha!\n Redefina sua senha para continuar!");
                            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    alteraSenha();
                                }
                            }).create();
                            dialog.setCancelable(false);
                            dialog.show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                throw new Error("USUARIO INVALIDO");
            }
        });
        dialog = new Dialog(this);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Atenção");
        dialog.setMessage("Tem certeza que deseja sair?");
        dialog.setNegativeButton("Não", null);
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        }).create();
        dialog.show();
    }

    public void btn_main_to_perfil (View view){
        Intent it = new Intent(this, Perfil.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        it.putExtras(parametros);
        startActivity(it);
    }

    public void btn_main_to_card (View view) {
        Intent it = new Intent(this, Card.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        parametros.putString("status_usuario", "DEFAUT");
        parametros.putBoolean("prestador", user.getPrestador());
        it.putExtras(parametros);
        startActivity(it);
    }


    public void btn_main_to_prestadores(View view) {
        Intent it = new Intent(this, Prestadores.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        parametros.putString("status", "DEFAUT");
        it.putExtras(parametros);
        startActivity(it);
    }

    public void btn_main_to_solicitacoes(View view) {
        Intent it = new Intent(this, Pedidos.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        parametros.putString("status", "PEDIDOS");
        it.putExtras(parametros);
        startActivity(it);
    }

    public void btn_main_to_servicos(View view) {
        Intent it = new Intent(this, Servicos.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        it.putExtras(parametros);
        startActivity(it);
    }

    @SuppressLint("SetTextI18n")
    public void alteraSenha() {
        RetrofitService retrofitService = new RetrofitService();
        API api = retrofitService.getRetrofit().create(API.class);

        dialog.setContentView(R.layout.z_custom_alertdialog_alter_password);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextInputEditText edtInsiraNovaSenha = dialog.findViewById(R.id.edtInsiraNovaSenha);
        TextInputEditText edtConfirmaNovaSenha = dialog.findViewById(R.id.edtConfirmaNovaSenha);
        MaterialTextView ttvTextEmailAlterSenha = dialog.findViewById(R.id.ttvTextEmailAlterSenha);
        TextInputEditText edtCodConfirmEmailAlterSenha = dialog.findViewById(R.id.edtCodConfirmEmailAlterSenha);
        MaterialTextView btnReenviarCodigoAlterSenha = dialog.findViewById(R.id.btnReenviarCodigoAlterSenha);
        MaterialButton btnConfirmaAlteracaoSenha = dialog.findViewById(R.id.btnConfirmaAlteracaoSenha);
        ProgressBar progressBar = dialog.findViewById(R.id.progressBarAlterSenha);

        progressBar.setVisibility(View.VISIBLE);
        edtInsiraNovaSenha.setEnabled(false);
        edtConfirmaNovaSenha.setEnabled(false);
        btnConfirmaAlteracaoSenha.setEnabled(false);
        btnReenviarCodigoAlterSenha.setEnabled(false);

        api.codConfirmacaoEmail(user.getEmail()).enqueue(new Callback<Return>() {
            @Override
            public void onResponse(Call<Return> call, Response<Return> response) {
                codConfirmacao = response.body().getText();
                progressBar.setVisibility(View.GONE);
                edtInsiraNovaSenha.setEnabled(true);
                edtConfirmaNovaSenha.setEnabled(true);
                btnConfirmaAlteracaoSenha.setEnabled(true);
                btnReenviarCodigoAlterSenha.setEnabled(true);
            }

            @Override
            public void onFailure(Call<Return> call, Throwable t) {

            }
        });
        btnConfirmaAlteracaoSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novaSenha = String.valueOf(edtInsiraNovaSenha.getText());
                novaSenhaConfirm = String.valueOf(edtConfirmaNovaSenha.getText());
                novaSenhaCodConfirmacao = String.valueOf(edtCodConfirmEmailAlterSenha.getText());
                if (novaSenha.equals(novaSenhaConfirm)){
                    if (novaSenha.length()>=5) {
                        if (novaSenhaCodConfirmacao.equals(codConfirmacao)) {
                            progressBar.setVisibility(View.VISIBLE);
                            edtInsiraNovaSenha.setEnabled(false);
                            edtConfirmaNovaSenha.setEnabled(false);
                            btnConfirmaAlteracaoSenha.setEnabled(false);
                            btnReenviarCodigoAlterSenha.setEnabled(false);
                            api.alterarSenha(idUsuario, novaSenha).enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    Toast.makeText(MainActivity.this, "Senha Alterada com Sucesso!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    edtInsiraNovaSenha.setEnabled(true);
                                    edtConfirmaNovaSenha.setEnabled(true);
                                    btnConfirmaAlteracaoSenha.setEnabled(true);
                                    btnReenviarCodigoAlterSenha.setEnabled(true);
                                    dialog.hide();
                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {

                                }
                            });
                        } else {
                            Toast.makeText(MainActivity.this, "O código de confirmação incorreto!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Senha deve possuir ao menos 5 caractes!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "As senhas informadas não são iguais ", Toast.LENGTH_SHORT).show();
                    edtInsiraNovaSenha.selectAll();
                    edtConfirmaNovaSenha.setText("");
                }
            }
        });

        btnReenviarCodigoAlterSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                progressBar.setVisibility(View.VISIBLE);
                edtInsiraNovaSenha.setEnabled(false);
                edtConfirmaNovaSenha.setEnabled(false);
                btnConfirmaAlteracaoSenha.setEnabled(false);
                btnReenviarCodigoAlterSenha.setEnabled(false);
                api.codConfirmacaoEmail(user.getEmail()).enqueue(new Callback<Return>() {
                    @Override
                    public void onResponse(Call<Return> call, Response<Return> response) {
                        codConfirmacao = response.body().getText();
                        progressBar.setVisibility(View.GONE);
                        edtInsiraNovaSenha.setEnabled(true);
                        edtConfirmaNovaSenha.setEnabled(true);
                        btnConfirmaAlteracaoSenha.setEnabled(true);
                        btnReenviarCodigoAlterSenha.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<Return> call, Throwable t) {
                    }
                });
            }
        });
        dialog.show();
        ttvTextEmailAlterSenha.setText("Um codigo de confirmacao foi enviado para o \n E-mail: "+user.getEmail()+"\n Coloque-o abaixo para prosseguir!");
    }

    @SuppressLint("SetTextI18n")
    public void info_cards(View view) {
        dialog.setContentView(R.layout.z_custom_alertdialog_information);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MaterialTextView infoTitle = dialog.findViewById(R.id.ttvInfoTitle);
        MaterialTextView infoDesc = dialog.findViewById(R.id.ttvInfoDesc);
        MaterialButton btnConfirmaInfo = dialog.findViewById(R.id.btnConfirmaInfo);

        infoTitle.setText("Cartões");
        infoDesc.setText("Cartões");

        btnConfirmaInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    public void info_prestadores(View view) {
        dialog.setContentView(R.layout.z_custom_alertdialog_information);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MaterialTextView infoTitle = dialog.findViewById(R.id.ttvInfoTitle);
        MaterialTextView infoDesc = dialog.findViewById(R.id.ttvInfoDesc);
        MaterialButton btnConfirmaInfo = dialog.findViewById(R.id.btnConfirmaInfo);

        infoTitle.setText("Prestadores");
        infoDesc.setText("Prestadores");

        btnConfirmaInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    public void info_servicos(View view) {
        dialog.setContentView(R.layout.z_custom_alertdialog_information);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MaterialTextView infoTitle = dialog.findViewById(R.id.ttvInfoTitle);
        MaterialTextView infoDesc = dialog.findViewById(R.id.ttvInfoDesc);
        MaterialButton btnConfirmaInfo = dialog.findViewById(R.id.btnConfirmaInfo);

        infoTitle.setText("Serviços");
        infoDesc.setText("Serviços");

        btnConfirmaInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    public void info_pedidos(View view) {
        dialog.setContentView(R.layout.z_custom_alertdialog_information);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MaterialTextView infoTitle = dialog.findViewById(R.id.ttvInfoTitle);
        MaterialTextView infoDesc = dialog.findViewById(R.id.ttvInfoDesc);
        MaterialButton btnConfirmaInfo = dialog.findViewById(R.id.btnConfirmaInfo);

        infoTitle.setText("Pedidos");
        infoDesc.setText("Pedidos");

        btnConfirmaInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
        dialog.show();
    }
}