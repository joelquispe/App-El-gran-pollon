package com.example.appelgranpollon.Models

data class OrderDetailsData(
    val id:Int? = null,
    val quantity:Int,
    val price:Double,
    val order:OrderData,
    val product:PlateData
)
