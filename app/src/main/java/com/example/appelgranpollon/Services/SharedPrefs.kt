package com.example.appelgranpollon.Services

import android.content.Context

class SharedPrefs(val context:Context) {
    val SHARED_NAME = "pollon";
    val SHARED_USER = "user";

    val storage = context.getSharedPreferences(SHARED_NAME,0);
    fun saveUser(user:String){
        storage.edit().putString(SHARED_USER,user).apply();
    }

    fun getUser():String{
        return storage.getString(SHARED_USER,"null")!!;
    }
}