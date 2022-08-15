package com.lajotasoftware.goservice.Frames;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.lajotasoftware.goservice.Entity.Usuario;
import com.lajotasoftware.goservice.MainActivity;
import com.lajotasoftware.goservice.R;
import com.lajotasoftware.goservice.retrofit.RetrofitService;
import com.lajotasoftware.goservice.retrofit.UsuarioAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cadastro extends AppCompatActivity {
    Long idUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it  = getIntent();
        Bundle paramentros = it.getExtras();
        String status = paramentros.getString("status_usuario");
        if (status.equals("CADASTRAR_USUARIO")) {
            setContentView(R.layout.entry_cadastro_login);
            initializeComponentsCadastroLogin();
        } else if (status.equals("LOGIN_CRIADO")) {
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
        TextInputEditText inputEditTextPrimeiroNome = findViewById(R.id.edtPrimeiroNome);
        TextInputEditText inputEditTextSegundoNome = findViewById(R.id.edtSegundoNome);
        TextInputEditText inputEditTextCNPJCPF = findViewById(R.id.edtCNPJCPF);
        TextInputEditText inputEditTextTelefone = findViewById(R.id.edtTelefone);
        TextInputEditText inputEditTextEmail = findViewById(R.id.edtEmail);
        TextInputEditText inputEditTextSite = findViewById(R.id.edtSite);
        TextInputEditText inputEditTextRuaAvenida = findViewById(R.id.edtRuaAvenida);
        TextInputEditText inputEditTextNumero = findViewById(R.id.edtNumero);
        TextInputEditText inputEditTextBairro = findViewById(R.id.edtBairro);
        TextInputEditText inputEditTextCEP = findViewById(R.id.edtCEP);
        TextInputEditText inputEditTextCidade = findViewById(R.id.edtCidade);
        TextInputEditText inputEditTextUF = findViewById(R.id.edtUF);

        MaterialButton btn_gravar_usuario = findViewById(R.id.btnGravarCadUser);
        MaterialButton btn_cancelar_usuario = findViewById(R.id.btnCancelarCadUser);

        RetrofitService retrofitService = new RetrofitService();
        UsuarioAPI usuarioAPI = retrofitService.getRetrofit().create(UsuarioAPI.class);

        btn_gravar_usuario.setOnClickListener(view -> {
            String primeiroNome = String.valueOf(inputEditTextPrimeiroNome.getText());
            String segundoNome = String.valueOf(inputEditTextSegundoNome.getText());
            String CNPJCPF = String.valueOf(inputEditTextCNPJCPF.getText());
            String ruaAvenida = String.valueOf(inputEditTextRuaAvenida.getText());
            String bairro = String.valueOf(inputEditTextBairro.getText());
            String numero = String.valueOf(inputEditTextNumero.getText());
            String cep = String.valueOf(inputEditTextCEP.getText());
            String cidade = String.valueOf(inputEditTextCidade.getText());
            String uf = String.valueOf(inputEditTextUF.getText());
            String telefone = String.valueOf(inputEditTextTelefone.getText());
            String email = String.valueOf(inputEditTextEmail.getText());
            String site = String.valueOf(inputEditTextSite.getText());

            //validacao dos campos
            if (!(primeiroNome.equals(""))){
            if (!(segundoNome.equals(""))){
            if (!(CNPJCPF.equals(""))){
            if (!(ruaAvenida.equals(""))){
            if (!(bairro.equals(""))){
            if (!(numero.equals(""))){
            if (!(cep.equals(""))){
            if (!(cidade.equals(""))){
            if (!(uf.equals(""))){
            if (!(telefone.equals(""))){
            if (!(email.equals(""))){
                Usuario usuario = new Usuario();
                usuario.setPrimeiroNome(primeiroNome);
                usuario.setSegundoNome(segundoNome);
                usuario.setCpf(CNPJCPF);
                //usuario.setCnpj(CNPJCPF);
                usuario.setRuaAvenida(ruaAvenida);
                usuario.setBairro(bairro);
                usuario.setNumero(numero);
                usuario.setCep(cep);
                usuario.setCidade(cidade);
                usuario.setUf(uf);
                usuario.setTelefone(telefone);
                usuario.setEmail(email);
                usuario.setSite(site);

                Intent it = new Intent(this, Login.class);

                usuarioAPI.update(idUsuario, usuario).enqueue(new Callback<Usuario>(){
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response){
                        Toast.makeText(Cadastro.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
                        startActivity(it);
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t){
                        Toast.makeText(Cadastro.this, "Falha ao salvar! \n Tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(Cadastro.this, "Campo E-mail está vazio!", Toast.LENGTH_SHORT).show();
            }
            }else{
                Toast.makeText(Cadastro.this, "Campo Telefone está vazio!", Toast.LENGTH_SHORT).show();
            }
            }else{
                Toast.makeText(Cadastro.this, "Campo UF está vazio!", Toast.LENGTH_SHORT).show();
            }
            }else{
                Toast.makeText(Cadastro.this, "Campo CIDADE está vazio!", Toast.LENGTH_SHORT).show();
            }
            }else{
                Toast.makeText(Cadastro.this, "Campo CEP está vazio!", Toast.LENGTH_SHORT).show();
            }
            }else{
                Toast.makeText(Cadastro.this, "Campo Numero está vazio!", Toast.LENGTH_SHORT).show();
            }
            }else{
                Toast.makeText(Cadastro.this, "Campo Bairro está vazio!", Toast.LENGTH_SHORT).show();
            }
            }else{
                Toast.makeText(Cadastro.this, "Campo Rua/Avenida está vazio!", Toast.LENGTH_SHORT).show();
            }
            }else{
                Toast.makeText(Cadastro.this, "Campo do CNPJ/CPF está vazio!", Toast.LENGTH_SHORT).show();
            }
            }else{
                Toast.makeText(Cadastro.this, "Segundo nome inválido!", Toast.LENGTH_SHORT).show();
            }
            }else{
                Toast.makeText(Cadastro.this, "Nome inválido!", Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancelar_usuario.setOnClickListener(view -> {
            Intent it = new Intent(this, Login.class);
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
