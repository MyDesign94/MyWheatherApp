package com.example.weatherapp.WeatherService.WeatherModels

import com.google.gson.annotations.SerializedName

data class Parts(
    @SerializedName("night") val night : Night,
    @SerializedName("morning") val morning : Morning,
    @SerializedName("day") val day : Day,
    @SerializedName("evening") val evening : Evening,
    @SerializedName("day_short") val day_short : Day_short,
    @SerializedName("night_short") val night_short : Night_short
)

