package com.knear.android.provider.response.block

import com.google.gson.annotations.SerializedName


data class Result(

    @SerializedName("author") var author: String? = null,
    @SerializedName("chunks") var chunks: ArrayList<Chunks> = arrayListOf(),
    @SerializedName("header") var header: Header = Header()

)
