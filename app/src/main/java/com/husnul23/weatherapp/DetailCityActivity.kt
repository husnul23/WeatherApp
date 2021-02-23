package com.husnul23.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail_city.*
import kotlinx.android.synthetic.main.activity_detail_city.view.*
import kotlinx.android.synthetic.main.activity_detail_city.humidityKota
import kotlinx.android.synthetic.main.activity_detail_city.kotaCuaca
import kotlinx.android.synthetic.main.activity_detail_city.pressureKota
import kotlinx.android.synthetic.main.activity_detail_city.suhuKota
import kotlinx.android.synthetic.main.activity_detail_city.tempMax
import kotlinx.android.synthetic.main.activity_detail_city.tempMin
import kotlinx.android.synthetic.main.activity_detail_city.vibeKota
import org.json.JSONObject
import java.lang.Exception

class DetailCityActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CITY = "extra_city"
        const val USER_TOKEN = "41967ef9df3bcc3b1bc153c689771049"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_city)
        val intent = intent
        val cityName = intent.getStringExtra(EXTRA_CITY)

        kotaCuaca.visibility = View.GONE
        tempMin.visibility = View.GONE
        tempMax.visibility = View.GONE
        vibeKota.visibility = View.GONE
        suhuKota.visibility = View.GONE
        pressureKota.visibility = View.GONE
        humidityKota.visibility = View.GONE

        getCityDetail(cityName)
    }

    private fun getCityDetail(cityName: String?) {
        val client = AsyncHttpClient()
        val url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + USER_TOKEN
        progressBar.visibility = View.VISIBLE

        client.addHeader("Authorization", DetailCityActivity.USER_TOKEN)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                progressBar.visibility = View.GONE
                val listCity = ArrayList<Weather>()
                val result = String(responseBody!!)
                try {
                    val item = JSONObject(result)
                    val details = item.getJSONArray("weather")
                    val main = item.getJSONObject("main")
                    val weather = Weather()

                    val city = item.getString("name")
                    val temp = main.getString("temp")
                    val temperatureMin = main.getString("temp_min")
                    val temperatureMax = main.getString("temp_max")
                    val pressure = main.getString("pressure")
                    val himidity = main.getString("humidity")
                    val feelsLike = main.getString("feels_like")

                    kotaCuaca.visibility = View.VISIBLE
                    tempMin.visibility = View.VISIBLE
                    tempMax.visibility = View.VISIBLE
                    vibeKota.visibility = View.VISIBLE
                    suhuKota.visibility = View.VISIBLE
                    pressureKota.visibility = View.VISIBLE
                    humidityKota.visibility = View.VISIBLE

                    weather.temp = temp
                    weather.temp_min = temperatureMin
                    weather.temp_max = temperatureMax
                    weather.pressure = pressure
                    weather.himidity = himidity
                    weather.feels_like = feelsLike
                    weather.city = city

                    kotaCuaca.setText(city)
                    tempMin.setText("Temperature Min :$temperatureMin")
                    tempMax.setText("Temperature Max :$temperatureMax")
                    vibeKota.setText("Feels Like :$feelsLike")
                    suhuKota.setText("$temp Celcius")
                    pressureKota.setText("Pressure :$pressure")
                    humidityKota.setText("Humidity :$himidity")


                    for (i in 0 until details.length()) {
                        val detail = details.getJSONObject(i)
                        val description = detail.getString("description")

                        weather.description = description
                        listCity.add(weather)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@DetailCityActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("DetailActivity", "salah")
            }
        })
    }
}