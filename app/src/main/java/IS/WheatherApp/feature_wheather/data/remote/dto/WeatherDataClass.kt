package com.example.weatherapp.WeatherService.WeatherModels

import com.google.gson.annotations.SerializedName

data class WeatherDataClass(
    @SerializedName("now") val now:Long,
    @SerializedName("now_dt") val now_dt: String,
    @SerializedName("info") val info: Info,
    @SerializedName("fact") val fact: Fact,
    @SerializedName("forecasts") val forecasts : List<Forecasts>
)
