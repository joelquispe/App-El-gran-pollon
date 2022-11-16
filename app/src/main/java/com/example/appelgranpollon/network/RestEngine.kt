package com.example.appelgranpollon.network

import android.os.StrictMode
import com.example.appelgranpollon.Models.ClientData
import com.google.gson.Gson
import okhttp3.CookieJar
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.Objects

class RestEngine {
    companion object{
        fun getRestEngine():Retrofit{
            //sosaya
            val ipsosaya = "http://192.168.18.45:8090/";

            //chipana
            val ipchipana = "http://192.168.1.4:8090/";

            //frank
            val ipfrank = ""

            val interceptor = HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            val cookieManager: CookieManager = CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectNetwork().penaltyDialog().permitNetwork().build())
            val cliente = OkHttpClient.Builder().cookieJar(JavaNetCookieJar(cookieManager)).followRedirects(false).addInterceptor(interceptor).build();
            val retrofit = Retrofit.Builder().baseUrl(ipsosaya).addConverterFactory( GsonConverterFactory.create()).client(cliente).build();

            return retrofit;
        }
    }

    fun  writeErrorBody(clase:Class<Object>,resp:String ):Any{
        return Gson().getAdapter(clase).fromJson(resp);
    }
}