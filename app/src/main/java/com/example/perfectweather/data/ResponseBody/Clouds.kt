package com.example.perfectweather.data.ResponseBody

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)