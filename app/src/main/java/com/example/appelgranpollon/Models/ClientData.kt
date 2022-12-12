package com.example.appelgranpollon.Models

data class ClientData (
    val id:Int? = null,
    val name:String="",
    val lastname:String="",
    val dateofbirth:String="",
    val email:String="",
    val password:String="",
    val phone:String="",
    var fcmtoken:String=""


);

