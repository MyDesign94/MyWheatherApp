package IS.WheatherApp.feature_wheather.presentation.main_wheather

import com.example.weatherapp.WeatherService.WeatherModels.WeatherDataClass

data class MainWeatherState(
    val isLoading: Boolean = false,
    val name: String = "",
    val cityId: Int = -1,
    val weatherData: WeatherDataClass? = null,
    val error: String = ""
)
