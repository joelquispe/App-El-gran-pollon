package com.example.appelgranpollon.Models

data class CartITemData(
    val idCartItem:Int? = null,
    var quantity:String,
    val product:PlateData? = null,
    val cart:CartData? = null
)
