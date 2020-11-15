package com.example.perfectweather.data

import com.example.perfectweather.BuildConfig
import com.example.perfectweather.data.ResponseBody.UrlModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//api.openweathermap.org/data/2.5/weather?q=Moscow&appid=3ec22cbc51a6590f139bc19051525acd&lang=ru


const val key = BuildConfig.API_KEY
//const val  API_KEY = "3ec22cbc51a6590f139bc19051525acd"

interface ApiInterface {
    @GET("weather")
    fun getCurrentWeather(
        @Query("q") location: String,
        @Query("lang") languageCode: String = "ru"
    ): Deferred<UrlModel>

    companion object{
        operator fun invoke(): ApiInterface{
            val requestInterceptor = Interceptor{chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid", key)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }
}