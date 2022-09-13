package com.example.submission1.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("data")
    val data: ArrayList<DataResponse>
)
