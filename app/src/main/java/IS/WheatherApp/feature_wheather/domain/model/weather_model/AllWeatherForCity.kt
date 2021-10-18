package IS.WheatherApp.feature_wheather.domain.model.weather_model

import com.example.weatherapp.WeatherService.WeatherModels.WeatherDataClass

data class AllWeatherForCity(
    val id: Int,
    val lat: String,
    val lon: String,
    val weather: WeatherDataClass? = null
)
