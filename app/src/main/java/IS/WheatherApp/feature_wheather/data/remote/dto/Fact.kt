package com.example.weatherapp.WeatherService.WeatherModels

import com.google.gson.annotations.SerializedName

data class Fact(
    @SerializedName("temp") val temp : Int,
    @SerializedName("feels_like") val feels_like : Int,
    @SerializedName("icon") val icon : String,
    @SerializedName("condition") val condition : String,
    @SerializedName("wind_speed") val wind_speed : Double,
    @SerializedName("wind_gust") val wind_gust : Double,
    @SerializedName("wind_dir") val wind_dir : String,
    @SerializedName("pressure_mm") val pressure_mm : Int,
    @SerializedName("pressure_pa") val pressure_pa : Int,
    @SerializedName("humidity") val humidity : Int,
    @SerializedName("uv_index") val uv_index : Int,
    @SerializedName("soil_temp") val soil_temp : Int,
    @SerializedName("soil_moisture") val soil_moisture : Double,
    @SerializedName("daytime") val daytime : String,
    @SerializedName("polar") val polar : Boolean,
    @SerializedName("season") val season : String,
    @SerializedName("obs_time") val obs_time : Int,
//    @SerializedName("prec_strength") val prec_strength: Int,
    @SerializedName("source") val source : String
)
