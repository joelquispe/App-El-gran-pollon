package com.example.appelgranpollon.Models

data class PlateData(
    val id:Int,
    val name:String,
    val price:Double,
    val stock: Int,
    val description:String,
    val status:Boolean,
    val image:String,
    val category:CategoryData

)