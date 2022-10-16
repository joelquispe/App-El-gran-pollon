package com.example.appelgranpollon.network

import com.example.appelgranpollon.Models.ClientData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET("cliente/buscar")
    fun verifyClient(@Query("email") email:String,@Query("password") password:String):Call<ClientData>;

    @GET("/cliente/listar")
    fun getClients():Call<List<ClientData>>;

    @Headers("Content-Type: application/vnd.api+json")
    @GET("/cliente/buscar/{id}")
    fun getClientById(@Path("id") id:Int):Call<ClientData>;


}