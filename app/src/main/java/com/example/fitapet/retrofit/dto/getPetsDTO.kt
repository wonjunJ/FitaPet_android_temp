package com.example.fitapet.retrofit.dto

//parameter

//response
data class getPets(
    val isSuccess:String,
    val code:Int,
    val message:String,
    val result:List<Pets>
)

data class Pets(
    val petId:Long,
    val petName:String,
    val petType:String,
    val petSpecies:String,
    val petBirth:String,
    val petSize:String,
    val petSex:String,
    val petAge:Int,
    val profileImg:String,
    val registrationType:String,
    val isNeutralize:String
)
