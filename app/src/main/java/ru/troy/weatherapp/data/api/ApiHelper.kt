package ru.troy.weatherapp.data.api

class ApiHelper(private val apiService: WeatherApi, private val city: String) {
    fun getDataFromApiHelper() = apiService.getWeather(city, "metric", "d3558ea8eea4c78fcba64fbf8cf5cd66")
}