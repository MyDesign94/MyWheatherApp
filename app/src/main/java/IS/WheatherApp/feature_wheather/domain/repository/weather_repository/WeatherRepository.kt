package IS.WheatherApp.feature_wheather.domain.repository.weather_repository

import com.example.weatherapp.WeatherService.WeatherModels.WeatherDataClass

interface WeatherRepository {

    suspend fun getWeather(
        lat: String,
        lon: String
    ): WeatherDataClass
}
