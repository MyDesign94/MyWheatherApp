package IS.WheatherApp.feature_wheather.presentation.nex_seven_days

import IS.WheatherApp.feature_wheather.domain.use_case.db_use_case.CitiesUseCase
import IS.WheatherApp.feature_wheather.domain.use_case.weather_use_case.WeatherUseCase
import IS.WheatherApp.feature_wheather.domain.util.Resource
import IS.WheatherApp.feature_wheather.domain.util.StandardLocation
import IS.WheatherApp.feature_wheather.presentation.main_wheather.MainWeatherState
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.WeatherService.WeatherModels.WeatherDataClass
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NextSevenDaysViewModel @Inject constructor(
    private val citiesUseCase: CitiesUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(MainWeatherState())
    val state: State<MainWeatherState> = _state

    init {
        savedStateHandle.get<String>("cityId")?.let { cityId ->
            viewModelScope.launch {
                val city = citiesUseCase.getCity(cityId.toInt())
                if (city != null) {
                    val cityData = Gson().fromJson(city.weatherData, WeatherDataClass::class.java)
                    _state.value = MainWeatherState(
                        cityId = cityId.toInt(),
                        weatherData = cityData,
                        name = city.name
                    )
                    Log.e("data", cityData.toString())
                }
            }
        }
    }


}