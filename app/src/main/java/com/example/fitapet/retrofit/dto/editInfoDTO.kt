package com.example.fitapet.retrofit.dto

data class editInfo(
    val isSuccess: String,
    val code: Int,
    val message: String
)

data class bodyClass(
    val age: Int,
    val tel: String,
    val addr: String
)