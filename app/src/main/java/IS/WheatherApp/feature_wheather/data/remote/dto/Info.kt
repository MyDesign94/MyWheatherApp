package com.example.weatherapp.WeatherService.WeatherModels

import com.google.gson.annotations.SerializedName

data class Info(

    @SerializedName("f") val f : Boolean,
    @SerializedName("n") val n : Boolean,
    @SerializedName("nr") val nr : Boolean,
    @SerializedName("ns") val ns : Boolean,
    @SerializedName("nsr") val nsr : Boolean,
    @SerializedName("p") val p : Boolean,
    @SerializedName("lat") val lat : Double,
    @SerializedName("lon") val lon : Double,
    @SerializedName("tzinfo") val tzinfo : Tzinfo,
    @SerializedName("def_pressure_mm") val def_pressure_mm : Int,
    @SerializedName("def_pressure_pa") val def_pressure_pa : Int,
    @SerializedName("_h") val _h : Boolean,
    @SerializedName("url") val url : String

)
