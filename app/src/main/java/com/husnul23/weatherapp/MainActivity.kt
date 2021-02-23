package com.husnul23.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvCities: RecyclerView
    private var list: ArrayList<Weather> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvCities = findViewById(R.id.rvWeather)
        rvCities.setHasFixedSize(true)

        list.addAll(CityData.listData)
        showRecycleList()
    }

    private fun showRecycleList() {
        rvCities.layoutManager = LinearLayoutManager(this)
        val listCityAdapter = ListCityAdapter(list)
        rvCities.adapter = listCityAdapter

        listCityAdapter.setOnItemClickCallback(object : ListCityAdapter.OnItemClickCallback {
            override fun onItemClick(data: Weather) {
                navigateToDetail(data)
            }
        })
    }

    fun navigateToDetail(wheater: Weather) {
        val intent = Intent(this, DetailCityActivity::class.java).apply {
            putExtra(DetailCityActivity.EXTRA_CITY, wheater.city)
        }
        startActivity(intent)
    }
}