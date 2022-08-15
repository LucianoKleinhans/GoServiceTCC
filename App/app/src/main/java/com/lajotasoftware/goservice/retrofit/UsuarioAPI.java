package com.lajotasoftware.goservice.retrofit;

import com.lajotasoftware.goservice.Entity.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioAPI {

    @GET("/usuario")
    Call<List<Usuario>> getAllUsuarios();

    //@POST("/usuario/update/{id}")
    //Call<Usuario> update(@Body Usuario usuario);

    @PUT("/usuario/update/{id}")
    Call<Usuario> update(@Path("id")Long id,@Body Usuario usuario);

    @POST("/usuario/validation")
    Call<Usuario> authentication(@Body Usuario usuario);

    @POST("/usuario/create")
    Call<Usuario> createNewUser(@Body Usuario usuario);
}
