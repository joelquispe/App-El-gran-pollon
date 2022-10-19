package com.example.appelgranpollon.network

import com.example.appelgranpollon.Models.ClientData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET("api/cliente/buscar")
    fun verifyClient(@Query("email") email:String,@Query("password") password:String):Call<ClientData>;

    @GET("api/cliente/listar")
    fun getClients():Call<List<ClientData>>;


    @GET("api/cliente/buscar/{id}")
    fun getClientById(@Path("id") id:Int):Call<ClientData>;


}