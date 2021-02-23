package com.husnul23.weatherapp

object CityData {
    private val listCities = arrayOf(
        "Jakarta",
        "Surabaya",
        "Bandung"
    )

    val listData: ArrayList<Weather>
    get() {
        val list = arrayListOf<Weather>()
        for (position in listCities.indices) {
            val weather = Weather()
            weather.city = listCities[position]
            list.add(weather)
        }
        return list
    }
}