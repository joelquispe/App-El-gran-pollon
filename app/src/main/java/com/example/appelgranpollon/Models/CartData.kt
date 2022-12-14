package com.example.appelgranpollon.Models

data class CartData(
    val id:Int? = null,
    var total:String = "",
    var isInOrder:Boolean = false,
    val cliente:ClientData? = null
)
