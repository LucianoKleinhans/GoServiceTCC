package com.lajotasoftware.goservice.retrofit;

import com.lajotasoftware.goservice.Entity.Categoria;
import com.lajotasoftware.goservice.Entity.Pedido;
import com.lajotasoftware.goservice.Entity.Proposta;
import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Entity.SubCategoria;
import com.lajotasoftware.goservice.Entity.Usuario;

import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
    //testa a conexão
    @POST ("/connection")
    Call<Boolean> testConnection();

    /*-----------------Crud Usuario-----------------*/

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

    @DELETE ("/usuario/delete/{id}")
    Call<Usuario> deleteUser(@Path("id")Long id);

    /*-----------------Crud Servico-----------------*/

    @POST ("/servico/create")
    Call<Servico> createNewService(@Body Servico servico);

    @POST ("/usuario/servico/{idPrestador}")
    Call<List<Servico>> getServicosPrestador(@Path("idPrestador")Long id);

    @PUT("/servico/update/{id}")
    Call<Servico> updateServico(@Path("id")Long id,@Body Servico servico);

    @POST("/servico/seleciona/{id}")
    Call<Servico> getServicoById(@Path("id") Long id);

    @DELETE("/servico/delete/{id}")
    Call<Servico> deleteServico(@Path("id")Long id);

    @POST ("/servico/lista/{id}")
    Call<List<Servico>> getAllServicos(@Path("id")Long id);

    /*-----------------Crud Card Serviço-----------------*/
    @POST ("/cardservico/create")
    Call<SolicitaServico> createNewCardService(@Body SolicitaServico solcitaServico);

    @PUT("/cardservico/update/{id}")
    Call<SolicitaServico> updateCardServico(@Path("id")Long id,@Body SolicitaServico solcitaServico);

    @POST("/cardservico/seleciona/{id}")
    Call<SolicitaServico> getCardServicoById(@Path("id") Long id);

    @POST ("/cardservico/meuscards/{id}")
    Call<List<SolicitaServico>> getCardServico(@Path("id")Long id);

    @POST ("/cardservico/cardspublicos/{id}")
    Call<List<SolicitaServico>> getCardsServicoPublico(@Path("id")Long id);

    //@DELETE("/cardservico/delete/{id}")
    //Call<SolicitaServico> deleteCardServico(@Path("id")Long id);



    /*-----------------View Prestadores-----------------*/

    @POST("/usuarioprestadores/{id}")
    Call<List<Usuario>> getAllPrestadores(@Path("id")Long id);

    @POST("/usuarioprestador/{id}")
    Call<Usuario> getPrestador(@Path("id")Long id);

    /*-----------------Criar pedido de serviço avulso-----------------*/
    @POST("/pedido")
    Call<Pedido> criarPedido(@Body Pedido pedido);

    @POST("/pedidosprestador/{id}")
    Call<List<Pedido>> getPedidosPrestador(@Path("id")Long id);

    @POST("/pedidoscliente/{id}")
    Call<List<Pedido>> getPedidosCliente(@Path("id")Long id);

    @POST("/pedidosprogresso/{id}")
    Call<List<Pedido>> getPedidosEmProgresso(@Path("id")Long id);

    @POST("/pedidosfinalizado/{id}")
    Call<List<Pedido>> getPedidosFinalizados(@Path("id")Long id);

    @POST("/pedido/{id}")
    Call<Pedido> getPedidoById(@Path("id")Long id);

    @PUT("/pedido/udpate/{id}")
    Call<Pedido> updatePedido(@Path("id")Long id,@Body Pedido pedido);

    /*-------------------------Categoria/SubCategoria-----------------------------*/

    @POST("/categoria")
    Call<List<Categoria>> getAllCategoria();

    @POST("/subcategoria/{id}")
    Call<List<SubCategoria>> getSubCategoria(@Path("id") Long id);

    /*------------------------- Proposta -----------------------------*/

    @POST("/proposta/create")
    Call<Proposta> criarProposta(@Body Proposta proposta);
}
