package com.example.fitapet.retrofit.dto

data class getCurrentServiceDTO (
    val isSuccess:String,
    val code:Int,
    val message:String,
    val result:currentDTO
    )
data class getCurrentServiceDtoNoResult(
    val isSuccess:String,
    val code:Int,
    val message:String,
)

data class currentDTO(
    val petSitterName: String,
    val category: String,
    val planStartTime: String,
    val customerName: String,
    val planEndTime: String,
    val customerRequestContent: String,
    val pets:List<ingPets>
)

data class ingPets(
    val petName:String,
    val petType:String,
    val petSpecies:String,
    val petSize:String,
    val petSex:String,
    val petAge:Int,
    val profileImg:String
)

