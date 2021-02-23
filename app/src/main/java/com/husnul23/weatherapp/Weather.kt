package com.husnul23.weatherapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather (
    var city: String = "",
    var description: String = "",
    var temp: String = "",
    var temp_min: String = "",
    var temp_max: String = "",
    var pressure: String = "",
    var himidity: String = "",
    var feels_like: String = ""
) : Parcelable
