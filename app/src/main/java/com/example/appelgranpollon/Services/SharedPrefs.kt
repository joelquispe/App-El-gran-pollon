package com.example.appelgranpollon.Services

import android.content.Context

class SharedPrefs(val context:Context) {
    val SHARED_NAME = "pollon";
    val SHARED_USER = "user";
    val SHARED_TYPE_USER = "typeuser";

    val storage = context.getSharedPreferences(SHARED_NAME,0);
    fun saveUser(user:String){
        storage.edit().putString(SHARED_USER,user).apply();
    }

    fun getUser():String{
        return storage.getString(SHARED_USER,"null")!!;
    }
    fun removeUser(){
        storage.edit().remove(SHARED_USER).commit();
    }
    fun saveTypeUser(typeuser:String){
        storage.edit().putString(SHARED_TYPE_USER,typeuser).apply();
    }
    fun getTypeUser():String{
        return storage.getString(SHARED_TYPE_USER,"null")!!;
    }
    fun removeTypeUser(){
        storage.edit().remove(SHARED_TYPE_USER).commit();
    }

}