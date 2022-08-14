package com.lajotasoftware.goservice.retrofit;

import com.lajotasoftware.goservice.Entity.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UsuarioAPI {

    @GET("/usuario")
    Call<List<Usuario>> getAllUsuarios();

    @POST("/usuario/update/{id}")
    Call<Usuario> update(@Body Usuario usuario);

    @POST("/usuario/validation")
    Call<Usuario> authentication(@Body Usuario usuario);
}
