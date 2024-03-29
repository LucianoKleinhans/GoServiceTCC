package com.lajotasoftware.goservice.retrofit;

import com.lajotasoftware.goservice.Entity.Categoria;
import com.lajotasoftware.goservice.Entity.Mensagem;
import com.lajotasoftware.goservice.Entity.Pedido;
import com.lajotasoftware.goservice.Entity.Proposta;
import com.lajotasoftware.goservice.Entity.Return;
import com.lajotasoftware.goservice.Entity.Servico;
import com.lajotasoftware.goservice.Entity.SolicitaServico;
import com.lajotasoftware.goservice.Entity.SubCategoria;
import com.lajotasoftware.goservice.Entity.Usuario;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    //testa a conexão
    @POST("/connection")
    Call<Boolean> testConnection();

    /*-----------------Crud Usuario-----------------*/

    @GET("/usuario")
    Call<List<Usuario>> getAllUsuarios();

    @POST("/usuario/getatualuser")
    Call<Usuario> getAtualUser(@Body Usuario usuario);

    @PUT("/usuario/update/{id}")
    Call<Usuario> update(@Path("id") Long id, @Body Usuario usuario);

    @POST("/usuario/validation")
    Call<Usuario> authentication(@Body Usuario usuario);

    @POST("/usuario/existe/{login}/{email}")
    Call<Return> existeUser(@Path("login") String login,
                            @Path("email") String email);

    @POST("/usuario/create")
    Call<Usuario> createNewUser(@Body Usuario usuario);

    @DELETE("/usuario/delete/{id}")
    Call<Usuario> deleteUser(@Path("id") Long id);

    @POST("/usuario/getusuario/{id}")
    Call<Usuario> getUsuario(@Path("id") Long id);

    /*-----------------Crud Servico-----------------*/

    @POST("/servico/create")
    Call<Servico> createNewService(@Body Servico servico);

    @POST("/usuario/servico/{idPrestador}")
    Call<List<Servico>> getServicosPrestador(@Path("idPrestador") Long id);

    @PUT("/servico/update/{id}")
    Call<Servico> updateServico(@Path("id") Long id, @Body Servico servico);

    @POST("/servico/seleciona/{id}")
    Call<Servico> getServicoById(@Path("id") Long id);

    @DELETE("/servico/delete/{id}")
    Call<Servico> deleteServico(@Path("id") Long id);

    @POST("/servico/lista/{id}")
    Call<List<Servico>> getAllServicos(@Path("id") Long id);

    @POST("/servico/lista/categoria/{idCategoria}/{idSubCategoria}/{idPrestador}")
    Call<List<Servico>> getAllServicosByCategoria(@Path("idCategoria") Long idCategoria,
                                                  @Path("idSubCategoria") Long idSubCategoria,
                                                  @Path("idPrestador") Long idPrestador);

    /*-----------------Crud Card Serviço-----------------*/
    @POST("/cardservico/create")
    Call<SolicitaServico> createNewCardService(@Body SolicitaServico solcitaServico);

    @PUT("/cardservico/update/{id}")
    Call<SolicitaServico> updateCardServico(@Path("id") Long id, @Body SolicitaServico solcitaServico);

    @POST("/cardservico/seleciona/{id}")
    Call<SolicitaServico> getCardServicoById(@Path("id") Long id);

    @POST("/cardservico/meuscards/{id}")
    Call<List<SolicitaServico>> getCardServico(@Path("id") Long id);

    @POST("/cardservico/cardspublicos/{id}")
    Call<List<SolicitaServico>> getCardsServicoPublico(@Path("id") Long id);

    //@DELETE("/cardservico/delete/{id}")
    //Call<SolicitaServico> deleteCardServico(@Path("id")Long id);

    @POST("/cardservico/cardsprogresso/{id}")
    Call<List<SolicitaServico>> getCardsServicoProgresso(@Path("id") Long id);

    @POST("/cardservico/cardsfinalizados/{id}")
    Call<List<SolicitaServico>> getCardsServicoFinalizados(@Path("id") Long id);

//    @POST("/cardservico/setstatus/{id}/{status}")
//    Call<Return> setStatusCardsServico(@Path("id") long id,
//                                       @Path("Status") String status);

    /*-----------------View Prestadores-----------------*/

    @POST("/usuarioprestadores/{id}")
    Call<List<Usuario>> getAllPrestadores(@Path("id") Long id);

    @POST("/usuarioprestador/{id}")
    Call<Usuario> getPrestador(@Path("id") Long id);

    /*-----------------Criar pedido de serviço avulso-----------------*/
    @POST("/pedido")
    Call<Pedido> criarPedido(@Body Pedido pedido);

    @POST("/pedidosprestador/{id}")
    Call<List<Pedido>> getPedidosPrestador(@Path("id") Long id);

    @POST("/pedidoscliente/{id}")
    Call<List<Pedido>> getPedidosCliente(@Path("id") Long id);

    @POST("/pedidosprogresso/{id}")
    Call<List<Pedido>> getPedidosEmProgresso(@Path("id") Long id);

    @POST("/pedidosfinalizado/{id}")
    Call<List<Pedido>> getPedidosFinalizados(@Path("id") Long id);

    @POST("/pedido/{id}")
    Call<Pedido> getPedidoById(@Path("id") Long id);

    @PUT("/pedido/udpate/{id}")
    Call<Pedido> updatePedido(@Path("id") Long id, @Body Pedido pedido);

    @POST("/pedido/status/statusPedido/{idPedido}/{status}")
    Call<Return> setStatusPedido(@Path("idPedido") Long idPedido,
                                   @Path("status") String status);

    @POST("/pedido/verificaseexiste/{idCliente}/{idServico}")
    Call<Return> verificaSeExiste(@Path("idCliente") Long idCliente,
                                  @Path("idServico") Long idServico);

    /*-------------------------Categoria/SubCategoria-----------------------------*/

    @POST("/categoria")
    Call<List<Categoria>> getAllCategoria();

    @POST("/subcategoria/{id}")
    Call<List<SubCategoria>> getSubCategoria(@Path("id") Long id);

    /*------------------------- Proposta -----------------------------*/

    @POST("/proposta/create")
    Call<Proposta> criarProposta(@Body Proposta proposta);

    @POST("/proposta/{id}")
    Call<Proposta> getPropostaByID(@Path("id") Long id);

    @POST("/proposta/enviadas/{id}")
    Call<List<Proposta>> getPropostasEnviadas(@Path("id") Long id);

    @POST("/proposta/recebidas/{id}")
    Call<List<Proposta>> getPropostasRecebidas(@Path("id") Long id);

    @PUT("/proposta/update/{id}")
    Call<Proposta> updateProposta(@Path("id") Long id, @Body Proposta proposta);

    @POST("/proposta/card/{idSolicitaServico}/{idPrestador}")
    Call<Proposta> getPropostaSolicitacaoServico(@Path("idSolicitaServico") Long idSolicitaServico,
                                                 @Path("idPrestador") Long idPrestador);

    @POST("/proposta/card/propostajafeita/{idprestador}/{idsolicitacao}")
    Call<Return> getPropostaJaFeita(@Path("idprestador") Long idPrestador,
                                    @Path("idsolicitacao") Long idSolicitacao);

    @POST("/proposta/card/statusProposta/{idproposta}/{status}")
    Call<Return> setStatusProposta(@Path("idproposta") Long idProposta,
                                   @Path("status") String status);
    /*------------------------------------------Mensagem------------------------------------------*/

    @POST("/mensagem/proposta/{id}")
    Call<List<Mensagem>> getPropostaMensagem(@Path("id") Long id);

    @POST("/mensagem/create")
    Call<Mensagem> createMensagem(@Body Mensagem mensagem);

    /*-------------------------------------------Imagem-------------------------------------------*/

    /*@Multipart
    @POST("/file/image/upload/{user}")
    Call <ResponseBody> savePhoto(
            @Part MultipartBody.Part image,
            @Path("user") Long idUser
    );*/

    @Multipart
    @POST("/file/image/upload")
    Call<ResponseBody> savePhoto(@Part("image") RequestBody image, @Part("user") Long idUser);

    @GET("/image/download/{imageName}")
    Call<ResponseBody> downloadPhoto(@Path("imageName") String imageName);

    /*-------------------------------------------E-Mail-------------------------------------------*/

    @POST("/usuario/password/forget_password/{email}")
    Call<Boolean> forgetPassword(@Path("email") String email);

    @POST("/usuario/confirmacaoemail/{email}")
    Call<Return> codConfirmacaoEmail(@Path("email") String email);

    @POST("/usuario/password/alter_password/{id}/{senha}")
    Call<Boolean> alterarSenha(@Path("id") Long id, @Path("senha") String senha);
}
