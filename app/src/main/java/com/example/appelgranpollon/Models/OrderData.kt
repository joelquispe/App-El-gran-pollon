package com.example.appelgranpollon.Models

import java.sql.Date

data class OrderData(
    val id:Int? = null,
    val order_date:Date,
    val total:String,
    val status:Boolean,
    val address:AddressData,
    val cliente:ClientData,
    val cart:CartData
)
