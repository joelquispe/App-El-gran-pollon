package com.example.appelgranpollon.network

import com.example.appelgranpollon.Models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    // consulta para loguearse
    @POST("api/cliente/buscar")
    fun verifyClient(@Query("email") email:String,@Query("password") password:String):Call<ClientData>;

    @POST("api/motorized/buscar")
    fun verifyMotorized(@Query("email") email:String,@Query("password") password:String):Call<MotorizedData>;

    @Headers("Content-Type: application/json")
    @POST("api/cliente/registrar")
    fun registerClient(@Body customer:ClientData):Call<ClientData>;


    @Headers("Content-Type: application/json")
    @PUT("api/cliente/editar/{id}")
    fun editClient(@Path("id") id: Int,@Body customer:ClientData):Call<ClientData>;

    @GET("api/cliente/listar")
    fun getClients():Call<List<ClientData>>;

    @GET("api/cliente/buscar/{id}")
    fun getClientById(@Path("id") id:Int):Call<ClientData>;

    @GET("api/plates/listar")
    fun findAllProducts():Call<List<PlateData>>;

    @GET("api/category/listar")
    fun findAllCategories():Call<List<CategoryData>>;

    //cart
    @Headers("Content-Type: application/json")
    @POST("api/cart/registrar")
    fun createCart(@Body cart:CartData):Call<CartData>;

    @GET("api/cart/listar/inOrder/{id}")
    fun findCartInOrder(@Path("id") id:Int):Call<List<CartData>>;

    @GET("api/cart/listar/notOrder/customer/{id}")
    fun findCartNotOrder(@Path("id") id:Int):Call<CartData>;

    @POST("api/cartItem/registrar")
    fun addItem(@Body cart:CartITemData):Call<CartITemData>;

    @PUT("api/cartItem/editar/quantity/{id}")
    fun editQuantityItem(@Path("id") id:Int,@Body cart:CartITemData):Call<CartITemData>;

    @POST("api/creditCard/registrar")
    fun createCard(@Body card:CardData):Call<CardData>;

    @POST("api/address/registrar")
    fun createAddress(@Body address:AddressData):Call<AddressData>;

    @GET("api/cartItem/listar/cart/{id}")
    fun getCartItemByCart(@Path("id") id: Int):Call<List<CartITemData>>

    @GET("api/order/listar/customer/{id}")
    fun getOrders(@Path("id") id:Int):Call<List<OrderData>>

    @POST("api/order/registrar")
    fun createOrder(@Body order:OrderData):Call<OrderData>
    @Headers("Content-Type: application/json")
    @GET("api/creditCard/listar/customer/{id}")
    fun getCardsByCustomer(@Path("id") id:Int):Call<List<CardData>>

    @Headers("Content-Type: application/json")
    @GET("api/address/listar/customer/{id}")
    fun getAddressByCustomer(@Path("id") id:Int):Call<List<AddressData>>

    @Headers("Content-Type: application/json")
    @PUT("api/cart/editar/{id}")
    fun editCart(@Path("id") id: Int,@Body cart:CartData):Call<CartData>;
}