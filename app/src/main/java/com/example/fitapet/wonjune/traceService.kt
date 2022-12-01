package com.example.fitapet.wonjune

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface traceService {

    @FormUrlEncoded
    @POST("/pets/send/")
    fun requestLoc(
        //인풋 정의(서버로 보낼 값)
        @Field("idStr") idStr:String
    ) : Call<traceLoc>
}