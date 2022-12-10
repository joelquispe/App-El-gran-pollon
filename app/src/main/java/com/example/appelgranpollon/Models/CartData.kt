package com.example.appelgranpollon.Models

data class CartData(
    val id:Int? = null,
    val total:String = "",
    val isInOrder:Boolean = false,
    val cliente:ClientData? = null
)
