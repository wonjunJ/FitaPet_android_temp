package com.example.fitapet.retrofit.dto

data class getUser(
    val isSuccess: String,
    val code: Int,
    val message: String,
    val result: List<UserSimple>
)

data class getDetail(
    val isSuccess: String,
    val code: Int,
    val message: String,
    val result: List<UserDetail>
)

data class UserSimple(
    val customerId: Long,
    val profileImgUrl: String,
    val customerName: String,
    val kakaoEmail: String
)

data class UserDetail(
    val address: String,
    val profileImgUrl: String,
    val customerName: String,
    val tel: String,
    val sex: String,
    val age: Int,
    val status: String
)