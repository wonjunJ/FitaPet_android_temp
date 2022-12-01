package com.example.fitapet.retrofit.dto

data class searchPsitter(
    val isSuccess: String,
    val code: Int,
    val message: String,
    val result: List<Petsitter>
)

data class Petsitter(
    val petSitterProfileImg: String,
    val petSitterName: String,
    val career: String,
    val hasPet_YN: String,
    val sex: String,
    val age: String,
    val selfIntroduction: String,
    val isAgreeToFilm_YN: String,
    val isAgreeSharingLocation_YN: String,
    val isWalkable_YN: String,
    val isPossibleCareOldPet_YN: String,
    val isBookMark: String
)