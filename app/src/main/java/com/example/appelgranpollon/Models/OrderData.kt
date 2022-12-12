package com.example.appelgranpollon.Models

import java.sql.Date

data class OrderData(
    val id:Int? = null,
    val orderDate:String,
    val total:String,
    val status:String,
    val address:AddressData,
    val cliente:ClientData,
    val cart:CartData
)
