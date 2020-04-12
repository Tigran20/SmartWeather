package ru.troy.weatherapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.troy.weatherapp.data.model.Weather
import ru.troy.weatherapp.data.repository.MainRepository
import ru.troy.weatherapp.utils.Resource

class WeatherViewModel : ViewModel() {
    private val mainRepository: MainRepository = MainRepository()

    fun getWeatherLiveData(): MutableLiveData<Resource<Weather>> {
        return mainRepository.fetchData()
    }

    fun getHelper(enterCity: String) {
        return mainRepository.getHelper(enterCity)
    }

}