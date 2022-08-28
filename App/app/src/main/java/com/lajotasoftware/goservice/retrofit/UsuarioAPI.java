package com.lajotasoftware.goservice.retrofit;

import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.Usuario;

import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioAPI {

    @GET("/usuario")
    Call<List<Usuario>> getAllUsuarios();

    @POST("/usuario/getatualuser")
    Call<Usuario> getAtualUser(@Body Usuario usuario);

    @PUT("/usuario/update/{id}")
    Call<Usuario> update(@Path("id")Long id,@Body Usuario usuario);

    @POST("/usuario/validation")
    Call<Usuario> authentication(@Body Usuario usuario);

    @POST("/usuario/create")
    Call<Usuario> createNewUser(@Body Usuario usuario);

    @POST ("/usuario/delete/{id}")
    Call<Usuario> deleteUser(@Path("id")Long id);

    @POST ("/connection")
    Call<Boolean> testConnection();

    @POST ("/servico/create")
    Call<Servico> createNewService(@Body Servico servico);

    @POST ("/usuario/servico/{idPrestador}")
    Call<List<Servico>> getServicosPrestador(@Path("idPrestador")Long id);

    @PUT("/servico/update/{id}")
    Call<Servico> update(@Path("id")Long id,@Body Servico servico);

    @POST("/servico/{nomeServ}/{descServ}/{valorServ}")
    Call<Servico> getServicoByNDV(@Path("nomeServ") String nomeServ,
                                  @Path("descServ") String descServ,
                                  @Path("valorServ") Double valorServ);


}
