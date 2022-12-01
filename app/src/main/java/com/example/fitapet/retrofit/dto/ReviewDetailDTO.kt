package com.example.fitapet.retrofit.dto

data class ReviewDetailDTO(
    val isSuccess:String,
    val code:Int,
    val message:String,
    val result:List<ReviewDetail>
)

data class ReviewDetail(
    val customerName:String,
    val profileImgOfCustomer:String,
    val reviewPicture:String,
    val isLike_YN:String,
    val reviewContent:String,
    val petType:String,
    val petSitterName:String,
    val petSitterProfileImg:String
)
