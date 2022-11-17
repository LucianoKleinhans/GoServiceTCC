package com.lajotasoftware.goservice.Frames;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.lajotasoftware.goservice.Entity.Categoria;
import com.lajotasoftware.goservice.Entity.Proposta;
import com.lajotasoftware.goservice.Entity.Return;
import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Entity.SubCategoria;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.Functions.Function;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.RetrofitService;
import com.lajotasoftware.goservice.retrofit.API;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cadastro extends AppCompatActivity {
    Usuario userLogado = new Usuario();
    Long idUsuario, idPrestador, idServico, idCategoria, idSubCategoria;
    String status, username, password, email, codConfirmacao;
    Intent it;
    Spinner uf, categoria_servico, sub_categoria_servico;
    API api;
    int auxiliar = 0, auxValor;
    Servico serv;
    Categoria categoria;
    SubCategoria subCategoria;
    List<Categoria> categorias = new ArrayList<>();
    List<SubCategoria> subCategorias = new ArrayList<>();
    Double valorServico = null;

    Dialog dialog;
    private final Function function = new Function();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it  = getIntent();
        Bundle parametros = it.getExtras();
        status = parametros.getString("status_usuario");

        if (status.equals("CADASTRAR_USUARIO")) {
            setContentView(R.layout.entry_cadastro_login);
            initializeComponentsCadastroLogin();
        } else if (status.equals("LOGIN_CRIADO")) {
            setContentView(R.layout.cadastro_usuario);
            idUsuario = parametros.getLong("id_usuario");
            initializeComponentsCadastro();
        }else if (status.equals("MODIFICA_CADASTRO")){
            setContentView(R.layout.cadastro_usuario);
            idUsuario = parametros.getLong("id_usuario");
            initializeComponentsCadastro();
        }else if (status.equals("CADASTRO_SERVICO")){
            setContentView(R.layout.cadastro_servico);
            idUsuario = parametros.getLong("id_usuario");
            initializeComponentsCadastroServico();
        }else if (status.equals("EDITAR_SERVICO")){
            setContentView(R.layout.cadastro_servico);
            idUsuario = parametros.getLong("id_usuario");
            idServico = parametros.getLong("id_servico");
            initializeComponentsCadastroServico();
        }
    }

    @SuppressLint("SetTextI18n")
    private void initializeComponentsCadastroLogin() {
        TextInputEditText inputEditTextUsuario = findViewById(R.id.edtCadastroLoginUser);
        TextInputEditText inputEditTextSenha = findViewById(R.id.edtCadastroLoginPass);
        TextInputEditText inputEditTextSenhaConfirm = findViewById(R.id.edtCadLoginConfirmPass);
        TextInputEditText inputEditTextEmail= findViewById(R.id.edtCadLoginEmail);
        TextInputEditText inputEditTextEmailConfirm = findViewById(R.id.edtCadLoginConfirmEmail);
        MaterialButton btnGravarUserAndPass = findViewById(R.id.btnCadLoginGravar);
        ProgressBar progressBarCadLogin = findViewById(R.id.progressBarCadLogin);
        progressBarCadLogin.setVisibility(View.GONE);
        dialog = new Dialog(this);

        btnGravarUserAndPass.setOnClickListener(view -> {
            username = String.valueOf(inputEditTextUsuario.getText());
            password = String.valueOf(inputEditTextSenha.getText());
            email = String.valueOf(inputEditTextEmail.getText());

            String confirmPassword = String.valueOf(inputEditTextSenhaConfirm.getText());
            String confirmEmail = String.valueOf(inputEditTextEmailConfirm.getText());

            Usuario usuario = new Usuario();
            usuario.setLogin(username);
            usuario.setSenha(password);
            usuario.setEmail(email);

            if (username.length()>=5){
                if (password.length()>=5){
                    if (Objects.equals(password, confirmPassword)){
                        if (Objects.equals(email, confirmEmail)){
                            progressBarCadLogin.setVisibility(View.VISIBLE);
                            inputEditTextUsuario.setEnabled(false);
                            inputEditTextSenha.setEnabled(false);
                            inputEditTextSenhaConfirm.setEnabled(false);
                            inputEditTextEmail.setEnabled(false);
                            inputEditTextEmailConfirm.setEnabled(false);
                            btnGravarUserAndPass.setEnabled(false);
                            RetrofitService retrofitService = new RetrofitService();
                            API api = retrofitService.getRetrofit().create(API.class);
                            api.existeUser(username, email).enqueue(new Callback<Return>() {
                                @Override
                                public void onResponse(Call<Return> call, Response<Return> response) {
                                    progressBarCadLogin.setVisibility(View.GONE);
                                    inputEditTextUsuario.setEnabled(true);
                                    inputEditTextSenha.setEnabled(true);
                                    inputEditTextSenhaConfirm.setEnabled(true);
                                    inputEditTextEmail.setEnabled(true);
                                    inputEditTextEmailConfirm.setEnabled(true);
                                    btnGravarUserAndPass.setEnabled(true);
                                    if (response.body()!= null){
                                        if (response.body().getStatusCode() == 200){
                                            dialog.setContentView(R.layout.z_custom_alertdialog_confirm_email);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                            MaterialTextView ttvDescConfirmEmail = dialog.findViewById(R.id.ttvDescConfirmEmail);
                                            TextInputEditText edtCodConfirmEmail = dialog.findViewById(R.id.edtCodConfirmEmail);
                                            MaterialButton btnConfirmaCodConfirmacao = dialog.findViewById(R.id.btnConfirmaCodConfirmacao);
                                            MaterialTextView btnReenviarCodigo = dialog.findViewById(R.id.btnReenviarCodigo);
                                            ProgressBar progressBarCodConfirmacao = dialog.findViewById(R.id.progressBarCodConfirmacao);

                                            progressBarCodConfirmacao.setVisibility(View.VISIBLE);
                                            edtCodConfirmEmail.setEnabled(false);
                                            btnConfirmaCodConfirmacao.setEnabled(false);
                                            btnReenviarCodigo.setEnabled(false);

                                            RetrofitService retrofitService = new RetrofitService();
                                            API api = retrofitService.getRetrofit().create(API.class);
                                            api.codConfirmacaoEmail(email).enqueue( new Callback<Return>() {
                                                @Override
                                                public void onResponse(Call<Return> call, Response<Return> response) {
                                                    codConfirmacao = response.body().getText();
                                                    progressBarCodConfirmacao.setVisibility(View.GONE);
                                                    edtCodConfirmEmail.setEnabled(true);
                                                    btnConfirmaCodConfirmacao.setEnabled(true);
                                                    btnReenviarCodigo.setEnabled(true);
                                                }

                                                @Override
                                                public void onFailure(Call<Return> call, Throwable t) {
                                                    Toast.makeText(Cadastro.this, "Falha ao enviar o codigo de confirmação tente novamente!", Toast.LENGTH_LONG).show();
                                                    progressBarCodConfirmacao.setVisibility(View.GONE);
                                                    edtCodConfirmEmail.setEnabled(true);
                                                    btnConfirmaCodConfirmacao.setEnabled(true);
                                                    btnReenviarCodigo.setEnabled(true);
                                                }
                                            });

                                            btnConfirmaCodConfirmacao.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    String codConfirm = String.valueOf(edtCodConfirmEmail.getText());
                                                    if (codConfirm.equals(codConfirmacao)) {
                                                        progressBarCodConfirmacao.setVisibility(View.VISIBLE);
                                                        edtCodConfirmEmail.setEnabled(false);
                                                        btnConfirmaCodConfirmacao.setEnabled(false);
                                                        btnReenviarCodigo.setEnabled(false);
                                                        Intent it = new Intent(Cadastro.this, Cadastro.class);
                                                        api.createNewUser(usuario).enqueue(new Callback<Usuario>() {
                                                            @Override
                                                            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                                                progressBarCodConfirmacao.setVisibility(View.GONE);
                                                                edtCodConfirmEmail.setEnabled(true);
                                                                btnConfirmaCodConfirmacao.setEnabled(true);
                                                                btnReenviarCodigo.setEnabled(true);
                                                                dialog.hide();

                                                                Toast.makeText(Cadastro.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
                                                                efetuarLogin(response.body());
                                                                Bundle parametros = new Bundle();
                                                                String status = "LOGIN_CRIADO";
                                                                parametros.putString("status_usuario", status);
                                                                parametros.putLong("id_usuario", idUsuario);
                                                                it.putExtras(parametros);
                                                                startActivity(it);
                                                            }

                                                            @Override
                                                            public void onFailure(Call<Usuario> call, Throwable t) {
                                                                progressBarCodConfirmacao.setVisibility(View.GONE);
                                                                edtCodConfirmEmail.setEnabled(true);
                                                                btnConfirmaCodConfirmacao.setEnabled(true);
                                                                btnReenviarCodigo.setEnabled(true);
                                                                Toast.makeText(Cadastro.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    } else {
                                                        Toast.makeText(Cadastro.this, "Codigo de confirmação inválido!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                            btnReenviarCodigo.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view){
                                                    progressBarCodConfirmacao.setVisibility(View.VISIBLE);
                                                    edtCodConfirmEmail.setEnabled(false);
                                                    btnConfirmaCodConfirmacao.setEnabled(false);
                                                    btnReenviarCodigo.setEnabled(false);
                                                    api.codConfirmacaoEmail(email).enqueue(new Callback<Return>() {
                                                        @Override
                                                        public void onResponse(Call<Return> call, Response<Return> response) {
                                                            progressBarCodConfirmacao.setVisibility(View.GONE);
                                                            edtCodConfirmEmail.setEnabled(true);
                                                            btnConfirmaCodConfirmacao.setEnabled(true);
                                                            btnReenviarCodigo.setEnabled(true);
                                                            codConfirmacao = response.body().getText();
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Return> call, Throwable t) {
                                                            progressBarCodConfirmacao.setVisibility(View.GONE);
                                                            edtCodConfirmEmail.setEnabled(true);
                                                            btnConfirmaCodConfirmacao.setEnabled(true);
                                                            btnReenviarCodigo.setEnabled(true);
                                                            Toast.makeText(Cadastro.this, "Falha ao enviar o codigo de confirmação tente novamente!", Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                            });
                                            dialog.show();

                                            ttvDescConfirmEmail.setText("O código de confirmação foi enviado para o e-mail \n"+email);
                                        }else{
                                            Toast.makeText(Cadastro.this, response.body().getText(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<Return> call, Throwable t) {
                                    progressBarCadLogin.setVisibility(View.GONE);
                                    inputEditTextUsuario.setEnabled(true);
                                    inputEditTextSenha.setEnabled(true);
                                    inputEditTextSenhaConfirm.setEnabled(true);
                                    inputEditTextEmail.setEnabled(true);
                                    inputEditTextEmailConfirm.setEnabled(true);
                                    btnGravarUserAndPass.setEnabled(true);
                                }
                            });
                        }else{
                            Toast.makeText(Cadastro.this, "E-mails informados não são iguais", Toast.LENGTH_SHORT).show();
                            inputEditTextEmail.selectAll();
                            inputEditTextEmailConfirm.setText("");
                        }
                    }else{
                        Toast.makeText(Cadastro.this, "Senhas informadas não são iguais", Toast.LENGTH_SHORT).show();
                        inputEditTextSenha.selectAll();
                        inputEditTextSenhaConfirm.setText("");
                    }
                }else{
                    Toast.makeText(Cadastro.this, "Senha deve possuir ao menos 5 caractes", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(Cadastro.this, "Tamanho do Nome de Usuário deve ser maior ou igual a 5", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void efetuarLogin(Usuario usuario) {
        userLogado = usuario;
        idUsuario = usuario.getId();
    }

    private void initializeComponentsCadastro() {
        uf = findViewById(R.id.spinner_uf);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.uf, android.R.layout.simple_spinner_item);
        uf.setAdapter(adapter);

        TextInputEditText inputEditTextPrimeiroNome = findViewById(R.id.edtPrimeiroNome);
        TextInputEditText inputEditTextSegundoNome = findViewById(R.id.edtSegundoNome);
        TextInputEditText inputEditTextCPF = findViewById(R.id.edtCPF);
        TextInputEditText inputEditTextCNPJ = findViewById(R.id.edtCNPJ);
        TextInputEditText inputEditTextTelefone = findViewById(R.id.edtTelefone);
        //TextInputEditText inputEditTextEmail = findViewById(R.id.edtEmail);
        TextInputEditText inputEditTextSite = findViewById(R.id.edtSite);
        TextInputEditText inputEditTextRuaAvenida = findViewById(R.id.edtRuaAvenida);
        TextInputEditText inputEditTextNumero = findViewById(R.id.edtNumero);
        TextInputEditText inputEditTextBairro = findViewById(R.id.edtBairro);
        TextInputEditText inputEditTextCEP = findViewById(R.id.edtCEP);
        TextInputEditText inputEditTextCidade = findViewById(R.id.edtCidade);

        Spinner spinnerUF = findViewById(R.id.spinner_uf);

        MaterialButton btn_gravar_usuario = findViewById(R.id.btnGravarCadUser);
        MaterialButton btn_cancelar_usuario = findViewById(R.id.btnCancelarCadUser);

        RetrofitService retrofitService = new RetrofitService();
        API usuarioAPI = retrofitService.getRetrofit().create(API.class);
        if (status.equals("MODIFICA_CADASTRO")){
            Usuario usuario = new Usuario();
            usuario.setId(idUsuario);
            usuarioAPI.getAtualUser(usuario).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> usuarioResponse) {
                    if (usuarioResponse.isSuccessful()) {
                        Usuario user = new Usuario();

                        assert usuarioResponse.body() != null;
                        user.setUsuario(usuarioResponse.body());
                        inputEditTextPrimeiroNome.setText(user.getPrimeiroNome());
                        inputEditTextSegundoNome.setText(user.getSegundoNome());
                        if (user.getCpf()!=null){
                            inputEditTextCPF.setText(Function.imprimeCPF(user.getCpf()));
                        }
                        if (user.getCnpj()!=null){
                            inputEditTextCNPJ.setText(Function.imprimeCNPJ(user.getCnpj()));
                        }
                        if (user.getTelefone()!=null){
                            inputEditTextTelefone.setText(Function.imprimeTelefone(user.getTelefone()));
                        }
                        //inputEditTextEmail.setText(user.getEmail());
                        inputEditTextSite.setText(user.getSite());
                        inputEditTextRuaAvenida.setText(user.getRuaAvenida());
                        inputEditTextNumero.setText(user.getNumero());
                        inputEditTextBairro.setText(user.getBairro());
                        inputEditTextCEP.setText(user.getCep());
                        inputEditTextCidade.setText(user.getCidade());
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Cadastro.this, R.array.uf, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerUF.setAdapter(adapter);
                        if (user.getUf() != null) {
                            int spinnerPosition = adapter.getPosition(user.getUf());
                            spinnerUF.setSelection(spinnerPosition);
                        }else{
                            spinnerUF.setSelection(0);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    throw new Error("USUARIO INVALIDO");
                }
            });
        }
        btn_gravar_usuario.setOnClickListener(view -> {
            String primeiroNome = String.valueOf(inputEditTextPrimeiroNome.getText());
            String segundoNome = String.valueOf(inputEditTextSegundoNome.getText());
            String CPF = String.valueOf(inputEditTextCPF.getText());
            String CNPJ = String.valueOf(inputEditTextCNPJ.getText());
            String ruaAvenida = String.valueOf(inputEditTextRuaAvenida.getText());
            String bairro = String.valueOf(inputEditTextBairro.getText());
            String numero = String.valueOf(inputEditTextNumero.getText());
            String cep = String.valueOf(inputEditTextCEP.getText());
            String cidade = String.valueOf(inputEditTextCidade.getText());
            String uf = spinnerUF.getSelectedItem().toString();
            String telefone = String.valueOf(inputEditTextTelefone.getText());
            //String email = String.valueOf(inputEditTextEmail.getText());
            String site = String.valueOf(inputEditTextSite.getText());
            Boolean validCPF = false;
            Boolean validCNPJ = false;
            //validacao dos campos
            if (!CPF.equals("")) {
                if (function.isCPF(CPF)) {
                    validCPF = true;
                } else {
                    Toast.makeText(Cadastro.this, "CPF inválido!", Toast.LENGTH_SHORT).show();
                }
            }
            if (!CNPJ.equals("")) {
                if (function.isCNPJ(CNPJ)) {
                    validCNPJ = true;
                } else {
                    Toast.makeText(Cadastro.this, "CNPJ inválido!", Toast.LENGTH_SHORT).show();
                }
            }
            if (!(primeiroNome.equals(""))) {
                if (!(segundoNome.equals(""))) {
                    if (((!(CNPJ.equals(""))) && (CPF.equals("")))||((!(CPF.equals(""))) && (CNPJ.equals("")))||((validCPF==true) && (validCNPJ==true))) {
                        if (!(ruaAvenida.equals(""))) {
                            if (!(bairro.equals(""))) {
                                if (!(numero.equals(""))) {
                                    if (!(cep.equals(""))) {
                                        if (!(cidade.equals(""))) {
                                            if (!(uf.equals(""))) {
                                                if ((!(telefone.equals(""))) && (function.validarTelefone(telefone))) {
                                                    //if ((!(email.equals("")))) {
                                                        Usuario usuario = new Usuario();
                                                        usuario.setPrimeiroNome(primeiroNome);
                                                        usuario.setSegundoNome(segundoNome);
                                                        usuario.setCpf(function.removeCaracteresEspeciais(CPF, "CPF"));
                                                        usuario.setCnpj(function.removeCaracteresEspeciais(CNPJ, "CNPJ"));
                                                        usuario.setRuaAvenida(ruaAvenida);
                                                        usuario.setBairro(bairro);
                                                        usuario.setNumero(numero);
                                                        usuario.setCep(cep);
                                                        usuario.setCidade(cidade);
                                                        usuario.setUf(uf);
                                                        usuario.setTelefone(function.removeCaracteresEspeciais(telefone, "TELEFONE"));
                                                        //usuario.setEmail(email);
                                                        usuario.setSite(site);
                                                        usuario.setAvaliacaoPrestador(0.0);
                                                        usuario.setAvaliacaoCliente(0.0);
                                                        usuario.setAtivo(true);
                                                        if (status.equals("LOGIN_CRIADO")) {
                                                            it = new Intent(this, Login.class);
                                                        }
                                                        if (status.equals("MODIFICA_CADASTRO")) {
                                                            it = new Intent(this, Perfil.class);
                                                            Bundle parametros = new Bundle();
                                                            parametros.putLong("id_usuario", idUsuario);
                                                            it.putExtras(parametros);
                                                        }
                                                        usuarioAPI.update(idUsuario, usuario).enqueue(new Callback<Usuario>() {
                                                            @Override
                                                            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                                                if (response.code() == 200) {
                                                                    Toast.makeText(Cadastro.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
                                                                    startActivity(it);
                                                                } else {
                                                                    Toast.makeText(Cadastro.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                            @Override
                                                            public void onFailure(Call<Usuario> call, Throwable t) {
                                                                Toast.makeText(Cadastro.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    //} else {Toast.makeText(Cadastro.this, "Campo E-mail está vazio ou incorreto!", Toast.LENGTH_SHORT).show();}
                                                } else {Toast.makeText(Cadastro.this, "Campo Telefone está vazio ou incorreto!", Toast.LENGTH_SHORT).show();}
                                            } else {Toast.makeText(Cadastro.this, "Campo UF está vazio!", Toast.LENGTH_SHORT).show();}
                                        } else {Toast.makeText(Cadastro.this, "Campo CIDADE está vazio!", Toast.LENGTH_SHORT).show();}
                                    } else {Toast.makeText(Cadastro.this, "Campo CEP está vazio!", Toast.LENGTH_SHORT).show();}
                                } else {Toast.makeText(Cadastro.this, "Campo Numero está vazio!", Toast.LENGTH_SHORT).show();}
                            } else {Toast.makeText(Cadastro.this, "Campo Bairro está vazio!", Toast.LENGTH_SHORT).show();}
                        } else {Toast.makeText(Cadastro.this, "Campo Rua/Avenida está vazio!", Toast.LENGTH_SHORT).show();}
                    }else{Toast.makeText(Cadastro.this, "CNPJ ou CPF deve ser preenchido!", Toast.LENGTH_SHORT).show();}
                } else {Toast.makeText(Cadastro.this, "Segundo nome inválido!", Toast.LENGTH_SHORT).show();}
            } else {Toast.makeText(Cadastro.this, "Nome inválido!", Toast.LENGTH_SHORT).show();}
        });
        btn_cancelar_usuario.setOnClickListener(view -> {
            if (status.equals("LOGIN_CRIADO")) {
                it = new Intent(this, Login.class);
            }if (status.equals("MODIFICA_CADASTRO")) {
                it = new Intent(this, Perfil.class);
                Bundle parametros = new Bundle();
                parametros.putLong("id_usuario", idUsuario);
                it.putExtras(parametros);
                finish();
            }
            usuarioAPI.deleteUser(idUsuario).enqueue(new Callback<Usuario>(){
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response){
                    //Toast.makeText(Cadastro.this, "Cadastro cancelado", Toast.LENGTH_SHORT).show();
                    startActivity(it);
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t){
                    //Toast.makeText(Cadastro.this, "Falha no cancelamento! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
    @SuppressLint("CutPasteId")
    private void initializeComponentsCadastroServico() {
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

        MaterialButton btn_gravar_servico = findViewById(R.id.btnGravarCadServico);
        MaterialButton btn_cancelar_servico = findViewById(R.id.btnCancelarCadServico);

        if (status.equals("CADASTRO_SERVICO")) {
            auxValor = 1;
            categoria_servico = findViewById(R.id.spinner_categoria);
            sub_categoria_servico = findViewById(R.id.spinner_sub_categoria);
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
                    idCategoria = Long.valueOf(i)+1;
                    RetrofitService retrofitServiceSubCategoria = new RetrofitService();
                    api = retrofitServiceSubCategoria.getRetrofit().create(API.class);
                    api.getSubCategoria(idCategoria).enqueue(new Callback<List<SubCategoria>>() {
                        @Override
                        public void onResponse(Call<List<SubCategoria>> call, Response<List<SubCategoria>> response) {
                            int aux = 0;
                            if (response.body() != null) {
                                aux = response.body().size();
                            }
                            if (aux > 0){
                                subCategorias.clear();
                                for (int i = 1; i <= aux; i++){
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
        if (status.equals("EDITAR_SERVICO")) {
            auxValor = 2;
            categoria_servico = findViewById(R.id.spinner_categoria);
            sub_categoria_servico = findViewById(R.id.spinner_sub_categoria);
            RetrofitService retrofitEditService = new RetrofitService();
            api = retrofitEditService.getRetrofit().create(API.class);
            api.getServicoById(idServico).enqueue(new Callback<Servico>() {
                @Override
                public void onResponse(Call<Servico> call, Response<Servico> servResponse) {
                    if (servResponse.isSuccessful()) {
                        serv = new Servico();
                        assert servResponse.body() != null;
                        serv.setServico(servResponse.body());
                        idServico = serv.getId();
                        inputEditTextNomeServico.setText(serv.getNome());
                        inputEditTextDescricaoServico.setText(serv.getObsServico());
                        inputEditTextValorServico.setText(NumberFormat.getCurrencyInstance().format((serv.getValor())));

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
                                    if (serv.getId_Categoria() != null) {
                                        spinnerCategoriaServico.setSelection(getIndex(spinnerCategoriaServico,serv.getId_Categoria().toString()));
                                        idCategoria = serv.getId_Categoria().getId();

                                        RetrofitService retrofitServiceSubCategoria = new RetrofitService();
                                        api = retrofitServiceSubCategoria.getRetrofit().create(API.class);
                                        api.getSubCategoria(idCategoria).enqueue(new Callback<List<SubCategoria>>() {
                                            @Override
                                            public void onResponse(Call<List<SubCategoria>> call, Response<List<SubCategoria>> response) {
                                                int aux = 0;
                                                if (response.body() != null) {
                                                    aux = response.body().size();
                                                }
                                                if (aux > 0){
                                                    subCategorias.clear();
                                                    for (int i = 1; i <= aux; i++){
                                                        subCategoria = new SubCategoria();
                                                        subCategoria.setId(response.body().get(i - 1).getId());
                                                        subCategoria.setNome(response.body().get(i - 1).getNome());
                                                        subCategoria.setIdCategoriaServico(response.body().get(i - 1).getIdCategoriaServico());
                                                        subCategorias.add(subCategoria);
                                                    }
                                                    ArrayAdapter<SubCategoria> adapter = new ArrayAdapter<SubCategoria>(getApplicationContext(), android.R.layout.simple_spinner_item, subCategorias);
                                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    sub_categoria_servico.setAdapter(adapter);
                                                    if (serv.getId_SubCategoria() != null) {
                                                        spinnerSubCategoriaServico.setSelection(getIndex(spinnerSubCategoriaServico,serv.getId_SubCategoria().toString()));
                                                        idSubCategoria = serv.getId_SubCategoria().getId();
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
                public void onFailure(Call<Servico> call, Throwable t) {
                    Toast.makeText(Cadastro.this, "Falha ao editar! \n Tente novamente. \n Se o problema persistir contate o suporte", Toast.LENGTH_SHORT).show();
                }
            });
            spinnerCategoriaServico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    idCategoria = Long.valueOf(i)+1;
                    RetrofitService retrofitServiceSubCategoria = new RetrofitService();
                    api = retrofitServiceSubCategoria.getRetrofit().create(API.class);
                    api.getSubCategoria(idCategoria).enqueue(new Callback<List<SubCategoria>>() {
                        @Override
                        public void onResponse(Call<List<SubCategoria>> call, Response<List<SubCategoria>> response) {
                            int aux = 0;
                            if (response.body() != null) {
                                aux = response.body().size();
                            }
                            if (aux > 0){
                                subCategorias.clear();
                                for (int i = 1; i <= aux; i++){
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
                idSubCategoria = Long.valueOf(String.copyValueOf(subCat.toCharArray(),0,pos-1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        inputEditTextValorServico.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            private String current = "";
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().equals(current)){
                    if ( auxValor == 1 ) {
                        inputEditTextValorServico.removeTextChangedListener(this);

                        String cleanString = s.toString().replaceAll("[R$.,  ]", "");
                        double parsed = Double.parseDouble(cleanString);

                        String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));
                        StringBuilder stringBuilder = new StringBuilder(cleanString);

                        if (cleanString.length() == 1) {
                            cleanString = formatted.replaceAll("[R$.,  ]", "");
                        }

                        cleanString = String.valueOf(stringBuilder.insert(cleanString.length() - 2, '.'));
                        valorServico = Double.parseDouble(cleanString);

                        current = formatted;
                        inputEditTextValorServico.setText(formatted);
                        inputEditTextValorServico.setSelection(formatted.length());

                        inputEditTextValorServico.addTextChangedListener(this);
                    } else {
                        auxValor = 1;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn_gravar_servico.setOnClickListener(view -> {
            String nomeServico = String.valueOf(inputEditTextNomeServico.getText());
            String descServico = String.valueOf(inputEditTextDescricaoServico.getText());
            if (!nomeServico.equals("") && nomeServico.length() >= 5) {
                if (valorServico > 0 && valorServico < 100000 && valorServico!=null) {
                    if (!descServico.equals("") && descServico.length() >= 10) {
                        Servico servico = new Servico();
                        categoria = new Categoria();
                        subCategoria = new SubCategoria();
                        categoria.setId(idCategoria);
                        subCategoria.setId(idSubCategoria);
                        servico.setNome(nomeServico);
                        servico.setValor(valorServico);
                        servico.setObsServico(descServico);
                        servico.setId_Categoria(categoria);
                        servico.setId_SubCategoria(subCategoria);
                        servico.setId_Prestador(user);

                        it = new Intent(this, Perfil.class);
                        Bundle parametros = new Bundle();
                        parametros.putLong("id_usuario", idUsuario);
                        it.putExtras(parametros);

                        if (status.equals("EDITAR_SERVICO")) {
                            api.updateServico(idServico, servico).enqueue(new Callback<Servico>() {
                                @Override
                                public void onResponse(Call<Servico> call, Response<Servico> response) {
                                    if (response.code() == 200) {
                                        Toast.makeText(Cadastro.this, "Serviço atualizado!", Toast.LENGTH_SHORT).show();
                                        startActivity(it);
                                        finish();
                                    } else {
                                        Toast.makeText(Cadastro.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Servico> call, Throwable t) {
                                    Toast.makeText(Cadastro.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (status.equals("CADASTRO_SERVICO")) {
                            api.createNewService(servico).enqueue(new Callback<Servico>() {
                                @Override
                                public void onResponse(Call<Servico> call, Response<Servico> response) {
                                    if (response.code() == 200) {
                                        Toast.makeText(Cadastro.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                        startActivity(it);
                                        finish();
                                    } else {
                                        Toast.makeText(Cadastro.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Servico> call, Throwable t) {
                                    Toast.makeText(Cadastro.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(Cadastro.this, "Descrição deve possuir \nno minimo 10 Caracteres!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Cadastro.this, "Valor é obigatório", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Cadastro.this, "Nome do serviço deve possuir \nno minimo 5 Caracteres!", Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancelar_servico.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
    }
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
}
