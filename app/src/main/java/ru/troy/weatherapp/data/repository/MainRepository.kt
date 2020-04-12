package ru.troy.weatherapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.troy.weatherapp.data.api.ApiHelper
import ru.troy.weatherapp.data.api.WeatherApp
import ru.troy.weatherapp.data.model.Weather
import ru.troy.weatherapp.utils.Resource

class MainRepository {

    private val weatherData = MutableLiveData<Resource<Weather>>()
    private val compositeDisposable = CompositeDisposable()
    private lateinit var apiHelper: ApiHelper

    private fun getDataFromRepository(): Single<Weather> {
        return apiHelper.getDataFromApiHelper()
    }

    fun getHelper(enterCity: String) {
        apiHelper = ApiHelper(WeatherApp.create(), enterCity)
    }

    fun fetchData() : MutableLiveData<Resource<Weather>>{
        compositeDisposable.add(
            getDataFromRepository()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        weatherData.postValue(Resource.success(it))
                        Log.e("WD", Resource.success(it.name).toString())
                    }
                    , {
                        weatherData.postValue(Resource.error("Неверные данные из сети!", null))
                        Log.e("Error", it.message)
                    })
        )

        return weatherData
    }
}