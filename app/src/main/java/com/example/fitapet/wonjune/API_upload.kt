package com.example.fitapet.wonjune

import android.database.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface API_upload {

    @FormUrlEncoded
    @POST("/pets/videolist/")
    fun sendFile(
        @Field("msg") msg: String
    ) : Call <List<OrderedDict>>
}

/*
interface API_download {

    @FormUrlEncoded
    @GET("/pets/download/")
    fun getFile(
        //@Query("fname") fname:String
    ) : Call<DownResponse>
}
 */