package com.example.appelgranpollon.Models

import java.sql.Date

data class PaymentDetailsData(
    val idpaymentdetails:Int? = null,
    val status:String,
    val provider:String,
    val amount:String,
    val created_at:Date,
    val order:OrderData,
    val methodpay:MethodPayData
)
