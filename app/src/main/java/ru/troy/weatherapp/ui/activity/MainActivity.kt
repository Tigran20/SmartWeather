package ru.troy.weatherapp.ui.activity

import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import ru.troy.weatherapp.R
import ru.troy.weatherapp.ui.viewmodel.WeatherViewModel
import ru.troy.weatherapp.utils.Status

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: WeatherViewModel
    private lateinit var countryTextView: TextView
    private lateinit var cityTextView: TextView
    private lateinit var tempTextView: TextView

    private lateinit var enterCity: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupViewModel()

        enterCity.onRightDrawableClicked {
            mainViewModel.getHelper(enterCity.text.toString())
            setupAPICall()
        }
    }

    private fun initViews() {
        countryTextView = country
        cityTextView = city
        tempTextView = temp
        enterCity = enter_city
    }

    private fun smartWeather(temp: Int) {
        when (temp) {
            in -30..0 -> {
                Toast.makeText(this, "Холодно, наденьте теплую одежду!", Toast.LENGTH_LONG).show()
            }
            in 0..10 -> {
                Toast.makeText(this, "Прохладно, одевайтесь теплее!", Toast.LENGTH_LONG).show()
            }
            in 10..30 -> {
                Toast.makeText(this, "Нормально, можно идти в повседеневноц одежде!", Toast.LENGTH_LONG).show()
            }
            in 30..50 -> {
                Toast.makeText(this, "Жарко, одевайтесь как можно легче!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupAPICall() {
        mainViewModel.getWeatherLiveData().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    countryTextView.text = resources.getString(R.string.country) + it.data!!.sys.country
                    cityTextView.text = resources.getString(R.string.city) + it.data.name
                    tempTextView.text = resources.getString(R.string.temp) + it.data.main.temp + resources.getString(R.string.tempC)
                    smartWeather(it.data.main.temp.toInt())
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        mainViewModel.getWeatherLiveData()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    }

    private fun EditText.onRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
        this.setOnTouchListener { v, event ->
            var hasConsumed = false
            if (v is EditText) {
                if (event.x >= v.width - v.totalPaddingRight) {
                    if (event.action == MotionEvent.ACTION_UP) {
                        onClicked(this)
                    }
                    hasConsumed = true
                }
            }
            hasConsumed
        }
    }
}
