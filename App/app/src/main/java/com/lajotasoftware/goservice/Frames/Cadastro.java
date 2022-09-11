package com.lajotasoftware.goservice.Frames;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.Functions.Function;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.RetrofitService;
import com.lajotasoftware.goservice.retrofit.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cadastro extends AppCompatActivity {
    Long idUsuario, idPrestador, idServico;
    String status;
    Intent it;
    Spinner uf, categoria_servico, sub_categoria_servico;
    API usuarioAPI;
    private Function function = new Function();
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

    private void initializeComponentsCadastroLogin() {
        TextInputEditText inputEditTextUsuario = findViewById(R.id.edtCadLoginUser);
        TextInputEditText inputEditTextSenha = findViewById(R.id.edtCadLoginPass);
        MaterialButton btnGravarUserAndPass = findViewById(R.id.btnCadLoginGravar);
        RetrofitService retrofitService = new RetrofitService();
        API usuarioAPI = retrofitService.getRetrofit().create(API.class);

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
                            inputEditTextCPF.setText(function.imprimeCPF(user.getCpf()));
                        }
                        if (user.getCnpj()!=null){
                            inputEditTextCNPJ.setText(function.imprimeCNPJ(user.getCnpj()));
                        }
                        if (user.getTelefone()!=null){
                            inputEditTextTelefone.setText(function.imprimeTelefone(user.getTelefone()));
                        }
                        inputEditTextEmail.setText(user.getEmail());
                        inputEditTextSite.setText(user.getSite());
                        inputEditTextRuaAvenida.setText(user.getRuaAvenida());
                        inputEditTextNumero.setText(user.getNumero());
                        inputEditTextBairro.setText(user.getBairro());
                        inputEditTextCEP.setText(user.getCep());
                        inputEditTextCidade.setText(user.getCidade());
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Cadastro.this, R.array.uf, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerUF.setAdapter(adapter);
                        if (!user.getUf().equals(null)) {
                            int spinnerPosition = adapter.getPosition(user.getUf());
                            spinnerUF.setSelection(spinnerPosition);
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
            String email = String.valueOf(inputEditTextEmail.getText());
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
                                                    if ((!(email.equals("")))) {
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
                                                    } else {Toast.makeText(Cadastro.this, "Campo E-mail está vazio ou incorreto!", Toast.LENGTH_SHORT).show();}
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
            categoria_servico = (Spinner) findViewById(R.id.spinner_categoria);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categoria_servico, android.R.layout.simple_spinner_item);
            categoria_servico.setAdapter(adapter);
            //String catServ = spinnerCategoriaServico.getSelectedItem().toString();

            sub_categoria_servico = (Spinner) findViewById(R.id.spinner_sub_categoria);
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sub_categoria_servico, android.R.layout.simple_spinner_item);
            sub_categoria_servico.setAdapter(adapter1);

            spinnerCategoriaServico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String catSelected = spinnerCategoriaServico.getSelectedItem().toString();
                    if (catSelected.equals(" ")) {
                        sub_categoria_servico = (Spinner) findViewById(R.id.spinner_sub_categoria);
                        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(Cadastro.this, R.array.sub_categoria_servico, android.R.layout.simple_spinner_item);
                        spinnerSubCategoriaServico.setAdapter(adapter1);
                    } else if (catSelected.equals("Informática")) {
                        sub_categoria_servico = (Spinner) findViewById(R.id.spinner_sub_categoria);
                        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(Cadastro.this, R.array.sub_categoria_servico_Informática, android.R.layout.simple_spinner_item);
                        spinnerSubCategoriaServico.setAdapter(adapter1);
                    } else if (catSelected.equals("Marcenaria")) {
                        sub_categoria_servico = (Spinner) findViewById(R.id.spinner_sub_categoria);
                        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(Cadastro.this, R.array.sub_categoria_servico_Marcenaria, android.R.layout.simple_spinner_item);
                        spinnerSubCategoriaServico.setAdapter(adapter2);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    Toast.makeText(Cadastro.this, "Nada Selecionado!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (status.equals("EDITAR_SERVICO")) {
            RetrofitService retrofitEditService = new RetrofitService();
            usuarioAPI = retrofitEditService.getRetrofit().create(API.class);
            usuarioAPI.getServicoById(idServico).enqueue(new Callback<Servico>() {
                @Override
                public void onResponse(Call<Servico> call, Response<Servico> servResponse) {
                    if (servResponse.isSuccessful()) {
                        Servico serv = new Servico();
                        assert servResponse.body() != null;
                        serv.setServico(servResponse.body());
                        idServico = serv.getId();
                        inputEditTextNomeServico.setText(serv.getNome());
                        inputEditTextDescricaoServico.setText(serv.getObsServico());
                        inputEditTextValorServico.setText(serv.getValor().toString());
                        ArrayAdapter<CharSequence> adapterCategoria = ArrayAdapter.createFromResource(Cadastro.this, R.array.categoria_servico, android.R.layout.simple_spinner_item);
                        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCategoriaServico.setAdapter(adapterCategoria);
                        if (!serv.getCategoria().equals(null)) {
                            int spinnerPosition = adapterCategoria.getPosition(serv.getCategoria());
                            spinnerCategoriaServico.setSelection(spinnerPosition);
                        }
                        if (serv.getCategoria().equals("Informática")) {
                            ArrayAdapter<CharSequence> adapterSubCategoria = ArrayAdapter.createFromResource(Cadastro.this, R.array.sub_categoria_servico_Informática, android.R.layout.simple_spinner_item);
                            adapterSubCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerSubCategoriaServico.setAdapter(adapterSubCategoria);
                            if (!serv.getSubCategoria().equals(null)) {
                                int spinnerPosition = adapterSubCategoria.getPosition(serv.getSubCategoria());
                                spinnerSubCategoriaServico.setSelection(spinnerPosition);
                            }
                        }
                        if (serv.getCategoria().equals("Marcenaria")) {
                            ArrayAdapter<CharSequence> adapterSubCategoria = ArrayAdapter.createFromResource(Cadastro.this, R.array.sub_categoria_servico_Marcenaria, android.R.layout.simple_spinner_item);
                            adapterSubCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerSubCategoriaServico.setAdapter(adapterSubCategoria);
                            if (!serv.getSubCategoria().equals(null)) {
                                int spinnerPosition = adapterSubCategoria.getPosition(serv.getSubCategoria());
                                spinnerSubCategoriaServico.setSelection(spinnerPosition);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Servico> call, Throwable t) {
                    Toast.makeText(Cadastro.this, "Falha ao editar! \n Tente novamente. \n Se o problema persistir contate o suporte", Toast.LENGTH_SHORT).show();
                }
            });
        }
        btn_gravar_servico.setOnClickListener(view -> {
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
                                Servico servico = new Servico();
                                servico.setNome(nomeServico);
                                servico.setValor(valorServico);
                                servico.setObsServico(descServico);
                                servico.setCategoria(catServico);
                                servico.setSubCategoria(subcatServico);
                                servico.setId_Prestador(user);

                                it = new Intent(this, Perfil.class);
                                Bundle parametros = new Bundle();
                                parametros.putLong("id_usuario", idUsuario);
                                it.putExtras(parametros);

                                if (status.equals("EDITAR_SERVICO")) {
                                    usuarioAPI.updateServico(idServico, servico).enqueue(new Callback<Servico>() {
                                        @Override
                                        public void onResponse(Call<Servico> call, Response<Servico> response) {
                                            if (response.code() == 200) {
                                                Toast.makeText(Cadastro.this, "Serviço atualizado!", Toast.LENGTH_SHORT).show();
                                                startActivity(it);
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
                                    usuarioAPI.createNewService(servico).enqueue(new Callback<Servico>() {
                                        @Override
                                        public void onResponse(Call<Servico> call, Response<Servico> response) {
                                            if (response.code() == 200) {
                                                Toast.makeText(Cadastro.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                                startActivity(it);
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
                                Toast.makeText(Cadastro.this, "Sub-Categoria não selecionada!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Cadastro.this, "Categoria não selecionada!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Cadastro.this, "Observação vazio!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Cadastro.this, "Valor vazio!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Cadastro.this, "Nome invalido!", Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancelar_servico.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}
