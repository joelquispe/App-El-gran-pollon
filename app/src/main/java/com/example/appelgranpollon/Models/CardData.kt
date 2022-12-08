package com.example.appelgranpollon.Models

data class CardData (
    val id_credit_cart:Int? = null,
    val cc_num:String = "",
    val expire_date:String = "",
    val number:String,
    val cliente:ClientData
)