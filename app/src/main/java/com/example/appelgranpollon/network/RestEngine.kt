package com.example.appelgranpollon.network

import android.os.StrictMode
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy


class RestEngine {
    companion object{
        fun getRestEngine():Retrofit{
            //sosaya
            val ipsosaya = "http://192.168.18.45:8090/";

            //chipana
            val ipchipana = "http://192.168.1.4:8090/";

            //frank
            val ipfrank = "http://192.168.18.69:8090/"

            val interceptor = HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setLenient()
                .create()
            val cookieManager: CookieManager = CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectNetwork().penaltyDialog().permitNetwork().build())
            val cliente = OkHttpClient.Builder().cookieJar(JavaNetCookieJar(cookieManager)).followRedirects(false).addInterceptor(interceptor).build();

            val retrofit = Retrofit.Builder().baseUrl(ipchipana).addConverterFactory( GsonConverterFactory.create(gson)).client(cliente).build();

            return retrofit;
        }
    }
    fun  writeErrorBody(clase:Class<Object>,resp:String ):Any{
        return Gson().getAdapter(clase).fromJson(resp);
    }
}