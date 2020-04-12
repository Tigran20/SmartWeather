package ru.troy.weatherapp.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.troy.weatherapp.data.model.Weather

interface WeatherApi {
    @GET("weather")
    fun getWeather(
        @Query("q") q: String,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): Single<Weather>
}