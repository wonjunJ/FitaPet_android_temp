package com.example.fitapet.retrofit.dto

data class ReviewDTO(
    val isSuccess:String,
    val code:Int,
    val message:String,
    val result:List<Review>
)

data class Review(
    val customerName:String,
    val profileImgOfCustomer:String,
    val reviewContent:String,
    val category:String,
    val petSitterName:String,
    val petSitterProfileImg:String
)
