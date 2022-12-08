package com.example.appelgranpollon.Models

data class AddressData(
    val id_address:Int? = null,
    val address:String,
    val city:String,
    val latitude:String,
    val longitude:String,
    val number:String,
    val reference:String,
    val street:String,
    val cliente:ClientData
)
