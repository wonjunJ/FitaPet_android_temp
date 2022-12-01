package com.example.fitapet.retrofit.dto

data class getBmark(
    val isSuccess: String,
    val code: Int,
    val message: String,
    val result: List<Bookmark>
)

data class Bookmark(
    val petSitterId: Int,
    val petSitterName: String,
    val sex: String,
    val age: Int,
    val careType: String,
    val isAgreeSharingLocation_YN: String,
    val isAgreeToFilm_YN: String,
    val isPossibleCareOldPet_YN: String,
    val isWalkable_YN: String
)