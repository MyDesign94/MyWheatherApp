package IS.WheatherApp.feature_wheather.domain.model.weather_model

import com.example.weatherapp.WeatherService.WeatherModels.WeatherDataClass

data class CurrentCityWeather(
    val id: Int,
    val name: String = "",
    val icon: String = "",
    val lat: String = "",
    val lon: String = "",
    val tempNow: String = "",
    val weatherData: WeatherDataClass? = null
)
