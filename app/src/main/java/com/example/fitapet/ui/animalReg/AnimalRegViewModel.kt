package com.example.fitapet.ui.animalReg

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.user.model.BirthdayType

class AnimalRegViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is AnimalReg Fragment"
    }
    /*
    "petName":"청담이",
            "petType":"CAT",
            "petSpecies":"경북대 얼굴",
            "petBirth":"2012-02-28",
            "petSize":"SMALL",
            "petSex":"MALE",
            "petAge":"10",
            "registrationType":"INTERNAL",
            "isNeutralize":"Y"
    * */
    lateinit var petName:String
    lateinit var petSpecies:String
    lateinit var petBirth:String
    lateinit var petSize:String
    lateinit var petAge:String
    lateinit var registrationType:String
    lateinit var isNeutralize:String

//    fun setPetName(input:String){
//        petName=input
//    }
//    fun getPetName():String{
//        return petName
//    }

    val text: LiveData<String> = _text
}
