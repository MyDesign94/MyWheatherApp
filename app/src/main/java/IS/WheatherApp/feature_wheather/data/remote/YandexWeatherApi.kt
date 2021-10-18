package IS.WheatherApp.feature_wheather.data.remote

import IS.WheatherApp.BuildConfig.YANDEX_WEATHER_API_KEY
import com.example.weatherapp.WeatherService.WeatherModels.WeatherDataClass
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface YandexWeatherApi {

    @Headers(YANDEX_WEATHER_API_KEY)
    @GET("v2/forecast")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): WeatherDataClass
}
