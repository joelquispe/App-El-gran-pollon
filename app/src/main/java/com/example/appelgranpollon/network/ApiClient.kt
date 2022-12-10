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

    @GET("api/cart/listar/notOrder/{id}")
    fun findCartNotOrder(@Path("id") id:Int):Call<CartData>;

    @POST("api/cartItem/registrar")
    fun addItem(@Body cart:CartITemData):Call<CartITemData>;

    @PUT("api/cartItem/editar/quantity/{id}")
    fun editQuantityItem(@Path("id") id:Int,@Body cart:CartITemData):Call<CartITemData>;

    @POST("api/creditCard/registrar")
    fun createCard(@Body card:CardData):Call<CardData>;

    @POST("api/address/registrar")
    fun createAddress(@Body address:AddressData):Call<AddressData>;

}