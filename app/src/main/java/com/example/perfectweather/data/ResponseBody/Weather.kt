package com.example.perfectweather.data.ResponseBody

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)