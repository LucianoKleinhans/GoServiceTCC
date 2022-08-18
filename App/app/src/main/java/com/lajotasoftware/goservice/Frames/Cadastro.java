package com.lajotasoftware.goservice.Frames;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.Functions.Function;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.RetrofitService;
import com.lajotasoftware.goservice.retrofit.UsuarioAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cadastro extends AppCompatActivity {
    Long idUsuario;
    String status;
    Intent it;
    Spinner uf;
    private Function function = new Function();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it  = getIntent();
        Bundle paramentros = it.getExtras();
        status = paramentros.getString("status_usuario");

        if (status.equals("CADASTRAR_USUARIO")) {
            setContentView(R.layout.entry_cadastro_login);
            initializeComponentsCadastroLogin();
        } else if (status.equals("LOGIN_CRIADO")) {
            setContentView(R.layout.cadastro_usuario);
            idUsuario = paramentros.getLong("id_usuario");
            initializeComponentsCadastro();
        }else if (status.equals("MODIFICA_CADASTRO")){
            setContentView(R.layout.cadastro_usuario);
            idUsuario = paramentros.getLong("id_usuario");
            initializeComponentsCadastro();
        }
    }

    private void initializeComponentsCadastroLogin() {
        TextInputEditText inputEditTextUsuario = findViewById(R.id.edtCadLoginUser);
        TextInputEditText inputEditTextSenha = findViewById(R.id.edtCadLoginPass);
        MaterialButton btnGravarUserAndPass = findViewById(R.id.btnCadLoginGravar);
        RetrofitService retrofitService = new RetrofitService();
        UsuarioAPI usuarioAPI = retrofitService.getRetrofit().create(UsuarioAPI.class);

        btnGravarUserAndPass.setOnClickListener(view -> {
            String username = String.valueOf(inputEditTextUsuario.getText());
            String password = String.valueOf(inputEditTextSenha.getText());

            Usuario usuario = new Usuario();
            usuario.setLogin(username);
            usuario.setSenha(password);

            Intent it = new Intent(this, Cadastro.class);

            usuarioAPI.createNewUser(usuario).enqueue(new Callback<Usuario>(){
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response){
                    Toast.makeText(Cadastro.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
                    efetuarLogin(response.body().getId());

                    Bundle parametros = new Bundle();
                    String status = "LOGIN_CRIADO";
                    parametros.putString("status_usuario", status);
                    parametros.putLong("id_usuario", idUsuario);
                    it.putExtras(parametros);
                    startActivity(it);
                }
                @Override
                public void onFailure(Call<Usuario> call, Throwable t){
                    Toast.makeText(Cadastro.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    private void efetuarLogin(Long id) {
        idUsuario = id;
    }

    private void initializeComponentsCadastro() {
        uf = (Spinner) findViewById(R.id.spinner_uf);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.uf, android.R.layout.simple_spinner_item);
        uf.setAdapter(adapter);

        TextInputEditText inputEditTextPrimeiroNome = findViewById(R.id.edtPrimeiroNome);
        TextInputEditText inputEditTextSegundoNome = findViewById(R.id.edtSegundoNome);
        TextInputEditText inputEditTextCPF = findViewById(R.id.edtCPF);
        TextInputEditText inputEditTextCNPJ = findViewById(R.id.edtCNPJ);
        TextInputEditText inputEditTextTelefone = findViewById(R.id.edtTelefone);
        TextInputEditText inputEditTextEmail = findViewById(R.id.edtEmail);
        TextInputEditText inputEditTextSite = findViewById(R.id.edtSite);
        TextInputEditText inputEditTextRuaAvenida = findViewById(R.id.edtRuaAvenida);
        TextInputEditText inputEditTextNumero = findViewById(R.id.edtNumero);
        TextInputEditText inputEditTextBairro = findViewById(R.id.edtBairro);
        TextInputEditText inputEditTextCEP = findViewById(R.id.edtCEP);
        TextInputEditText inputEditTextCidade = findViewById(R.id.edtCidade);

        /*ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.uf, android.R.layout.simple_spinner_item);
        uf.setAdapter(adapter);
        Spinner spinnerUF = findViewById(R.id.spinner_uf);*/

        //TextInputEditText inputEditTextUF = findViewById(R.id.edtUF);

        MaterialButton btn_gravar_usuario = findViewById(R.id.btnGravarCadUser);
        MaterialButton btn_cancelar_usuario = findViewById(R.id.btnCancelarCadUser);

        RetrofitService retrofitService = new RetrofitService();
        UsuarioAPI usuarioAPI = retrofitService.getRetrofit().create(UsuarioAPI.class);
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
                        inputEditTextCPF.setText(user.getCpf());
                        inputEditTextCNPJ.setText(user.getCnpj());
                        inputEditTextTelefone.setText(user.getTelefone());
                        inputEditTextEmail.setText(user.getEmail());
                        inputEditTextSite.setText(user.getSite());
                        inputEditTextRuaAvenida.setText(user.getRuaAvenida());
                        inputEditTextNumero.setText(user.getNumero());
                        inputEditTextBairro.setText(user.getBairro());
                        inputEditTextCEP.setText(user.getCep());
                        inputEditTextCidade.setText(user.getCidade());
                        //inputEditTextUF.setText(user.getUf());
                        //int ufPosition = adapter.getPosition(user.getUf());
                        //spinnerUF.setSelection(ufPosition);
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
            //String uf = String.valueOf(inputEditTextUF.getText());
            //String uf = spinnerUF.getSelectedItem().toString();
            String telefone = String.valueOf(inputEditTextTelefone.getText());
            String email = String.valueOf(inputEditTextEmail.getText());
            String site = String.valueOf(inputEditTextSite.getText());

            //validacao dos campos
            if (!CPF.equals("")) {
                if (function.isCPF(CPF)) {
                    Boolean validCPF = true;
                } else {
                    Toast.makeText(Cadastro.this, "CPF inválido!", Toast.LENGTH_SHORT).show();
                }
            }
            if (!CNPJ.equals("")) {
                if (function.isCNPJ(CNPJ)) {
                    Boolean validCNPJ = true;
                } else {
                    Toast.makeText(Cadastro.this, "CNPJ inválido!", Toast.LENGTH_SHORT).show();
                }
            }
            if (!(primeiroNome.equals(""))) {
                if (!(segundoNome.equals(""))) {
                    if (((!(CNPJ.equals(""))) && (CPF.equals("")))||((!(CPF.equals(""))) && (CNPJ.equals("")))) {
                        if (!(ruaAvenida.equals(""))) {
                            if (!(bairro.equals(""))) {
                                if (!(numero.equals(""))) {
                                    if (!(cep.equals(""))) {
                                        if (!(cidade.equals(""))) {
                                            if (!(uf.equals(""))) {
                                                if ((!(telefone.equals(""))) && (function.validarTelefone(telefone))) {
                                                    if (!(email.equals(""))) {
                                                        Usuario usuario = new Usuario();
                                                        usuario.setPrimeiroNome(primeiroNome);
                                                        usuario.setSegundoNome(segundoNome);
                                                        usuario.setCpf(CPF);
                                                        usuario.setCnpj(CNPJ);
                                                        usuario.setRuaAvenida(ruaAvenida);
                                                        usuario.setBairro(bairro);
                                                        usuario.setNumero(numero);
                                                        usuario.setCep(cep);
                                                        usuario.setCidade(cidade);
                                                        //usuario.setUf(uf);
                                                        usuario.setTelefone(telefone);
                                                        usuario.setEmail(email);
                                                        usuario.setSite(site);
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
                                                    } else {Toast.makeText(Cadastro.this, "Campo E-mail está vazio!", Toast.LENGTH_SHORT).show();}
                                                } else {Toast.makeText(Cadastro.this, "Campo Telefone está vazio!", Toast.LENGTH_SHORT).show();}
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
            if (status == "LOGIN_CRIADO") {
                it = new Intent(this, Login.class);
            }if (status == "MODIFICA_CADASTRO") {
                it = new Intent(this, Perfil.class);
                Bundle parametros = new Bundle();
                parametros.putLong("id_usuario", idUsuario);
                it.putExtras(parametros);
            }
            usuarioAPI.deleteUser(idUsuario).enqueue(new Callback<Usuario>(){
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response){
                    Toast.makeText(Cadastro.this, "Cadastro cancelado", Toast.LENGTH_SHORT).show();
                    startActivity(it);
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t){
                    Toast.makeText(Cadastro.this, "Falha no cancelamento! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
