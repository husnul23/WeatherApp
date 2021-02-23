package com.husnul23.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class DetailCityActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CITY = "extra_city"
        const val USER_TOKEN = "41967ef9df3bcc3b1bc153c689771049"
    }

    lateinit var weather: Weather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_city)
        val intent = intent
        val cityName = intent.getStringExtra(EXTRA_CITY)

        getCityDetail(cityName)
    }

    private fun getCityDetail(cityName: String?) {
        val client = AsyncHttpClient()
        val url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + USER_TOKEN

//        val url = "https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$USER_TOKEN"
        client.addHeader("Authorization", DetailCityActivity.USER_TOKEN)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val listCity = ArrayList<Weather>()
                val result = String(responseBody!!)
                try {
                    val item = JSONObject(result)
                    val details = item.getJSONArray("weather")
                    val main = item.getJSONArray("main")

                    val weather = Weather()

                    for (i in 0 until main.length()) {
                        val info = main.getJSONObject(i)
                        val temp = info.getString("temp")
                        val tempMin = info.getString("temp_min")
                        val tempMax = info.getString("temp_max")
                        val pressure = info.getString("pressure")
                        val himidity = info.getString("humidity")
                        val feelsLike = info.getString("feels_like")

                        weather.temp = temp
                        weather.temp_min = tempMin
                        weather.temp_max = tempMax
                        weather.pressure = pressure
                        weather.himidity = himidity
                        weather.feels_like = feelsLike
                        listCity.add(weather)
                    }

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