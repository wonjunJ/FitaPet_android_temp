package com.example.fitapet.wonjune

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FileResponse(
    var id : Int,
    var title : String,
    var date : String,
)

/*
data class ListResponse(
    var video : MutableList<OrderedDict>
)
 */

data class OrderedDict(
    @SerializedName("id")
    @Expose
    var id : Int,
    @SerializedName("title")
    @Expose
    var title : String,
    @SerializedName("thumnail")
    @Expose
    var thumnail : String,
    @SerializedName("uploadedFile")
    @Expose
    var uploadedFile : String,
    @SerializedName("fileSize")
    @Expose
    var fileSize : Float,
    @SerializedName("dateTimeOfUpload")
    @Expose
    var dateTimeOfUpload : String
)

/*
data class DownResponse(
    var content_type : String
)
 */
