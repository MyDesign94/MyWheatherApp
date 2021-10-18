package com.example.weatherapp.WeatherService.WeatherModels

import com.google.gson.annotations.SerializedName

data class Forecasts(

    @SerializedName("date") val date : String,
    @SerializedName("date_ts") val date_ts : Double,
    @SerializedName("week") val week : Double,
    @SerializedName("sunrise") val sunrise : String,
    @SerializedName("sunset") val sunset : String,
    @SerializedName("rise_begin") val rise_begin : String,
    @SerializedName("set_end") val set_end : String,
    @SerializedName("moon_code") val moon_code : Double,
    @SerializedName("moon_text") val moon_text : String,
    @SerializedName("parts") val parts : Parts,
    @SerializedName("hours") val hours : List<Hours>

)
