package com.lajotasoftware.goservice.Frames;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.lajotasoftware.goservice.Adapter.CustomAdapterService;
import com.lajotasoftware.goservice.Entity.Return;
import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.MainActivity;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.RetrofitService;
import com.lajotasoftware.goservice.retrofit.API;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil extends AppCompatActivity implements CustomAdapterService.OnServicoListener {

    private Long idUsuario, idServico;
    private String local;
    Usuario usuario = new Usuario();
    Usuario user = new Usuario();
    private Boolean prestador;
    Servico servico = new Servico();
    String status, bio, novaSenha, novaSenhaConfirm, codConfirmacao, novaSenhaCodConfirmacao;
    Dialog dialog;
    ProgressBar progressBar;
    ProgressBar progressBarPerfilUsuario;
    ProgressBar progressBarEdtPerfilUsuario;
    ProgressBar progressBarEdtCadastroUsuario;

    private final int GALLERY_REQ_CODE = 1000;

    CustomAdapterService customAdapter;
    RecyclerView listServicosPrestador;
    List<Servico> servicos = new ArrayList<>();

    ImageView AvatarPerfil;
    ImageButton btnSelectAvatarPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        Bundle parametros = it.getExtras();
        idUsuario = parametros.getLong("id_usuario");
        local = "PERFIL";
        setContentView(R.layout.perfil_usuario);
        initializeComponents();
    }

    private void initializeComponents() {
        MaterialTextView textViewNomeUsuario = findViewById(R.id.ttvUsernamePerfilUser);
        MaterialTextView textViewCidadeUsuario = findViewById(R.id.ttvCidadePerfilUser);
        MaterialTextView textViewEmailUsuario = findViewById(R.id.ttvEmailPerfilUser);
        MaterialTextView textViewSiteUsuario = findViewById(R.id.ttvSitePerfilUser);
        MaterialTextView textViewBioUsuario = findViewById(R.id.textView6);
        MaterialTextView textViewAvaliacaoPrestador = findViewById(R.id.textView7);
        RatingBar ratingBarPefilPrestador = findViewById(R.id.ratingBarPefilPrestador);
        RatingBar ratingBarPefilCliente = findViewById(R.id.ratingBarPefilCliente);
        progressBarPerfilUsuario = findViewById(R.id.progressBarPerfilUsuario);
        progressBarPerfilUsuario.setVisibility(View.GONE);
        //layout
        View rectangleServico = findViewById(R.id.myRectangleView24);
        View rectangleAvaliacaoPrestador = findViewById(R.id.myRectangleView36);
        View btnAddServicos = findViewById(R.id.btnServicos);

        listServicosPrestador = findViewById(R.id.listServicosPrestador);
        progressBarPerfilUsuario.setVisibility(View.VISIBLE);
        RetrofitService retrofitService = new RetrofitService();
        API usuarioAPI = retrofitService.getRetrofit().create(API.class);
        usuario.setId(idUsuario);
        usuarioAPI.getAtualUser(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> usuarioResponse) {
                if (usuarioResponse.isSuccessful()) {

                    assert usuarioResponse.body() != null;
                    user.setUsuario(usuarioResponse.body());

                    /*Deixar botao prestador visivel*/

                    if (user.getPrestador().equals(true)) {
                        //btnTornarUserPrestador.setVisibility(View.INVISIBLE);
                        listServicosPrestador.setVisibility(View.VISIBLE);
                        btnAddServicos.setVisibility(View.VISIBLE);
                        rectangleServico.setVisibility(View.VISIBLE);
                        ratingBarPefilPrestador.setRating(user.getAvaliacaoPrestador().floatValue());
                        ratingBarPefilCliente.setRating(user.getAvaliacaoCliente().floatValue());
                        listaServico();
                    } else {
                        //btnTornarUserPrestador.setVisibility(View.VISIBLE);
                        textViewAvaliacaoPrestador.setVisibility(View.INVISIBLE);
                        rectangleAvaliacaoPrestador.setVisibility(View.INVISIBLE);
                        ratingBarPefilPrestador.setVisibility(View.INVISIBLE);
                        listServicosPrestador.setVisibility(View.INVISIBLE);
                        btnAddServicos.setVisibility(View.INVISIBLE);
                        rectangleServico.setVisibility(View.INVISIBLE);
                        ratingBarPefilCliente.setRating(user.getAvaliacaoCliente().floatValue());
                    }
                    textViewNomeUsuario.setText(user.getPrimeiroNome());
                    if(user.getCidade() != null){
                        textViewCidadeUsuario.setText("Cidade:" + user.getCidade() + " - " + user.getUf());
                    }
                    if(user.getEmail() != null) {
                        textViewEmailUsuario.setText("E-mail:" + user.getEmail());
                    }
                    if(user.getSite() == null){
                        textViewSiteUsuario.setVisibility(View.INVISIBLE);
                    }else{textViewSiteUsuario.setText("Site:" + user.getSite());}
                    textViewBioUsuario.setText(user.getBio());
                    bio = user.getBio();
                    progressBarPerfilUsuario.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                progressBarPerfilUsuario.setVisibility(View.GONE);
                Toast.makeText(Perfil.this, "Usuario Inválido!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        dialog = new Dialog(this);
    }

    private void listaServico() {
        progressBarPerfilUsuario.setVisibility(View.VISIBLE);
        RetrofitService retrofitServiceListService = new RetrofitService();
        API usuarioAPIListService = retrofitServiceListService.getRetrofit().create(API.class);
        usuarioAPIListService.getServicosPrestador(idUsuario).enqueue(new Callback<List<Servico>>() {
            @Override
            public void onResponse(Call<List<Servico>> call, Response<List<Servico>> response) {
                int aux = response.body().size();
                servicos.clear();
                for (int i=1; i<=aux;i++){
                    Servico servico = new Servico();
                    servico.setId(response.body().get(i-1).getId());
                    servico.setNome(response.body().get(i-1).getNome());
                    servico.setObsServico(response.body().get(i-1).getObsServico());
                    servico.setValor(response.body().get(i-1).getValor());
                    servicos.add(servico);
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                listServicosPrestador.setLayoutManager(linearLayoutManager);
                customAdapter = new CustomAdapterService(Perfil.this, servicos, Perfil.this);
                listServicosPrestador.setAdapter(customAdapter);
                progressBarPerfilUsuario.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Servico>> call, Throwable t) {
                Toast.makeText(Perfil.this, "Sem Sucesso ao carregar lista de serviço!", Toast.LENGTH_SHORT).show();
                progressBarPerfilUsuario.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onCardRemoverClick(int position, Long id) {
        AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Excluir Serviço");
        alertDialogBuilder
                .setMessage("Clique sim para Exluir o serviço!")
                .setCancelable(false)
                .setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Servico serv = new Servico();
                                serv.setId(id);
                                serv.setExcluido(true);
                                RetrofitService retrofitEditService = new RetrofitService();
                                API usuarioAPI = retrofitEditService.getRetrofit().create(API.class);
                                usuarioAPI.updateServico(id,serv).enqueue(new Callback<Servico>() {
                                    @Override
                                    public void onResponse(Call<Servico> call, Response<Servico> response) {
                                        Toast.makeText(Perfil.this, "Serviço exluído!", Toast.LENGTH_SHORT).show();
                                        listaServico();
                                    }

                                    @Override
                                    public void onFailure(Call<Servico> call, Throwable t) {
                                        Toast.makeText(Perfil.this, "Serviço exluído!", Toast.LENGTH_SHORT).show();
                                        listaServico();
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
        Intent it = new Intent(this,Cadastro.class);
        Bundle parametros = new Bundle();
        String status = "EDITAR_SERVICO";
        parametros.putLong("id_usuario", idUsuario);
        parametros.putString("status_usuario", status);
        parametros.putLong("id_servico",id);
        it.putExtras(parametros);
        startActivity(it);
    }

    public void btn_edtperfil_to_cad (View view){
        Intent it = new Intent(this, Cadastro.class);
        Bundle parametros = new Bundle();
        String status = "MODIFICA_CADASTRO";
        parametros.putString("status_usuario", status);
        parametros.putLong("id_usuario", idUsuario);
        it.putExtras(parametros);
        startActivity(it);
        local = "PERFIL";
        setContentView(R.layout.perfil_usuario);
        initializeComponents();
    }
    public void btn_perfil_to_editperfil (View view){
        status = "EDITAR_PERFIL";
        local = "EDIT_PERFIL";
        setContentView(R.layout.edit_perfil_usuario);
        initializeComponentsEdtPerfil();
    }
    public void btn_perfil_to_main (View view){
        Intent it = new Intent(this, MainActivity.class);
        Bundle parametros = new Bundle();
        parametros.putLong("id_usuario", idUsuario);
        it.putExtras(parametros);
        startActivity(it);
        finish();
    }
    public void btn_perfil_to_cadservico(View view) {
        Intent it = new Intent(this, Cadastro.class);
        Bundle parametros = new Bundle();
        String status = "CADASTRO_SERVICO";
        parametros.putLong("id_usuario", idUsuario);
        parametros.putString("status_usuario", status);
        it.putExtras(parametros);
        startActivity(it);
    }
    private void initializeComponentsEdtPerfil() {
        MaterialTextView textViewNomeUsuario = findViewById(R.id.ttvUsernamePerfilUser);
        MaterialTextView textViewCidadeUsuario = findViewById(R.id.ttvCidadePerfilUser);
        MaterialTextView textViewEmailUsuario = findViewById(R.id.ttvEmailPerfilUser);
        MaterialTextView textViewSiteUsuario = findViewById(R.id.ttvSitePerfilUser);
        TextView textViewBioUsuario = findViewById(R.id.editTextTextMultiLine);
        progressBarEdtPerfilUsuario = findViewById(R.id.progressBarEdtPerfilUsuario);
        textViewBioUsuario.setText(bio);

        //imageview
        AvatarPerfil = findViewById(R.id.avatarPerfil);

        MaterialButton btnTornarUserPrestador = findViewById(R.id.btnTornarPresPerfilUser);
        MaterialButton btnGravarEditPerfil = findViewById(R.id.btnGravarEditPerfil);
        MaterialButton btnCancelarEditPerfil = findViewById(R.id.btnCancelarEditPerfil);

        progressBarEdtPerfilUsuario.setVisibility(View.VISIBLE);
        RetrofitService retrofitService = new RetrofitService();
        API usuarioAPI = retrofitService.getRetrofit().create(API.class);
        usuario.setId(idUsuario);
        usuarioAPI.getAtualUser(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> usuarioResponse) {
                if (usuarioResponse.isSuccessful()) {

                    assert usuarioResponse.body() != null;
                    user.setUsuario(usuarioResponse.body());

                    /* Deixar botao prestador visivel */
                    if (user.getPrestador()){
                        btnTornarUserPrestador.setVisibility(View.INVISIBLE);
                    }else{
                        btnTornarUserPrestador.setVisibility(View.VISIBLE);
                    }

                    textViewNomeUsuario.setText(user.getPrimeiroNome());
                    if (user.getCidade() != null) {
                        textViewCidadeUsuario.setText("Cidade:" + user.getCidade() + " - " + user.getUf());
                    }
                    if (user.getEmail() != null){
                        textViewEmailUsuario.setText("E-mail:" + user.getEmail());
                    }
                    if(user.getSite() == null){
                        textViewSiteUsuario.setVisibility(View.INVISIBLE);
                    }else{textViewSiteUsuario.setText("Site:" + user.getSite());}

                    /*if (usuarioResponse.body().getAvatarImg() != null){
                        usuarioAPI.downloadPhoto(usuarioResponse.body().getAvatarImg()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                response.body();
                                //AvatarPerfil.setimage;
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }*/

                }
                progressBarEdtPerfilUsuario.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                progressBarEdtPerfilUsuario.setVisibility(View.GONE);
                throw new Error("USUARIO INVALIDO");
            }
        });

        btnGravarEditPerfil.setOnClickListener(view -> {
            String textBio = String.valueOf(textViewBioUsuario.getText());
            user.setBio(textBio);
            usuarioAPI.update(idUsuario, user).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.code() == 200) {
                        Toast.makeText(Perfil.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(Perfil.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(Perfil.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                }
            });
            setContentView(R.layout.perfil_usuario);
            initializeComponents();
        });
        btnCancelarEditPerfil.setOnClickListener(view -> {
            setContentView(R.layout.perfil_usuario);
            initializeComponents();
        });
        btnTornarUserPrestador.setOnClickListener(view -> {
            AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Tornar Usuario Prestador");
            alertDialogBuilder
                    .setMessage("Clique sim para se tornar Prestador!")
                    .setCancelable(false)
                    .setPositiveButton("Sim",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    RetrofitService retrofitService = new RetrofitService();
                                    API usuarioAPI = retrofitService.getRetrofit().create(API.class);

                                    Usuario usuario = new Usuario();

                                    usuario.setId(idUsuario);
                                    usuario.setId_Prestador(idUsuario+100);
                                    usuario.setPrestador(true);
                                    usuarioAPI.update(idUsuario, usuario).enqueue(new Callback<Usuario>() {
                                        @Override
                                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                            if (response.code() == 200) {
                                                Toast.makeText(Perfil.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
                                                Usuario user = new Usuario();
                                                assert response.body() != null;
                                                user.setUsuario(response.body());
                                                onBackPressed();
                                                dialog.cancel();
                                            } else {
                                                Toast.makeText(Perfil.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Usuario> call, Throwable t) {
                                            Toast.makeText(Perfil.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
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
        });
    }

    public void btnSelectAvatarPerfil(View view) {
        Intent itGallery = new Intent(Intent.ACTION_PICK);
        itGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(itGallery, GALLERY_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode==GALLERY_REQ_CODE){
                AvatarPerfil.setImageURI(data.getData());
                uploadFile(data.getData());
            }
        }
    }

    private void uploadFile(Uri data) {
        File file = new File(data.getPath());
        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        /*RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                //.addFormDataPart("user", String.valueOf(idUsuario))
                .build();*/

        RetrofitService retrofitService = new RetrofitService();
        API api = retrofitService.getRetrofit().create(API.class);
        api.savePhoto(fbody,idUsuario).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(Perfil.this, "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void alterarSenha(View view) {
        RetrofitService retrofitService = new RetrofitService();
        API api = retrofitService.getRetrofit().create(API.class);

        dialog.setContentView(R.layout.z_custom_alertdialog_alter_password);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextInputEditText edtInsiraNovaSenha = dialog.findViewById(R.id.edtInsiraNovaSenha);
        TextInputEditText edtConfirmaNovaSenha = dialog.findViewById(R.id.edtConfirmaNovaSenha);
        MaterialTextView ttvTextEmailAlterSenha = dialog.findViewById(R.id.ttvTextEmailAlterSenha);
        TextInputEditText edtCodConfirmEmailAlterSenha = dialog.findViewById(R.id.edtCodConfirmEmailAlterSenha);
        MaterialTextView btnReenviarCodigoAlterSenha = dialog.findViewById(R.id.btnReenviarCodigoAlterSenha);
        MaterialButton btnConfirmaAlteracaoSenha = dialog.findViewById(R.id.btnConfirmaAlteracaoSenha);

        progressBar = dialog.findViewById(R.id.progressBarAlterSenha);
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
                                    Toast.makeText(Perfil.this, "Senha Alterada com Sucesso!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Perfil.this, "O código de confirmação incorreto!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Perfil.this, "Senha deve possuir ao menos 5 caractes!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Perfil.this, "As senhas informadas não são iguais ", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed(){
        if (local.equals("PERFIL")){
            Intent it = new Intent(this, MainActivity.class);
            Bundle parametros = new Bundle();
            parametros.putLong("id_usuario", idUsuario);
            it.putExtras(parametros);
            startActivity(it);
            finish();
        } else if (local.equals("EDIT_PERFIL")){
            local = "PERFIL";
            setContentView(R.layout.perfil_usuario);
            initializeComponents();
        }
    }
}