package com.example.fitapet.retrofit.dto

data class getStatus(
    val isSuccess:String,
    val code:Int,
    val message:String,
    val result:statusRslt
)

data class statusRslt(
    val status: String
)

data class getAddr(
    val isSuccess:String,
    val code:Int,
    val message:String,
    val result:addressInfo
)

data class addressInfo(
    val customerId: Long,
    val address: String
)