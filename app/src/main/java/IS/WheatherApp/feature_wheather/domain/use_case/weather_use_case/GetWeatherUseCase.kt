package IS.WheatherApp.feature_wheather.domain.use_case.weather_use_case

import IS.WheatherApp.feature_wheather.domain.repository.weather_repository.WeatherRepository
import IS.WheatherApp.feature_wheather.domain.util.Resource
import com.example.weatherapp.WeatherService.WeatherModels.WeatherDataClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(
        lat: String,
        lon: String
    ): Flow<Resource<WeatherDataClass>> = flow {
        try {
            emit(Resource.Loading<WeatherDataClass>())
            val weatherData = repository.getWeather(lat, lon)
            emit(Resource.Success<WeatherDataClass>(weatherData))
        } catch (e: HttpException) {
            emit(
                Resource.Error<WeatherDataClass>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error<WeatherDataClass>(
                    "Couldn't reach server. Check your internet connections"
                )
            )
        }
    }
}
