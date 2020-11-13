package com.example.perfectweather.data.ResponseBody

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("speed")
    val speed: Int
)