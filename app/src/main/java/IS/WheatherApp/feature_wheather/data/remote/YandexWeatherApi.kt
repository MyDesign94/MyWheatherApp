package IS.WheatherApp.feature_wheather.data.remote

import com.example.weatherapp.WeatherService.WeatherModels.WeatherDataClass
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.*

interface YandexWeatherApi {


    @Headers("")
    @GET("v2/forecast")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ) : WeatherDataClass
}