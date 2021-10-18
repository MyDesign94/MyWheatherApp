package IS.WheatherApp.feature_wheather.data.repository.weather_repository

import IS.WheatherApp.feature_wheather.data.remote.YandexWeatherApi
import IS.WheatherApp.feature_wheather.domain.repository.weather_repository.WeatherRepository
import com.example.weatherapp.WeatherService.WeatherModels.WeatherDataClass
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: YandexWeatherApi
): WeatherRepository {
    override suspend fun getWeather(
        lat: String,
        lon: String
    ): WeatherDataClass {
        return api.getWeather(lat, lon)
    }
}