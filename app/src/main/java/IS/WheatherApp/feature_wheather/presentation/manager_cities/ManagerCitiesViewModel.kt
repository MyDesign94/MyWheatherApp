package IS.WheatherApp.feature_wheather.presentation.manager_cities

import IS.WheatherApp.feature_wheather.domain.model.City
import IS.WheatherApp.feature_wheather.domain.model.weather_model.CurrentCityWeather
import IS.WheatherApp.feature_wheather.domain.use_case.db_use_case.CitiesUseCase
import IS.WheatherApp.feature_wheather.domain.use_case.weather_use_case.WeatherUseCase
import IS.WheatherApp.feature_wheather.domain.util.Resource
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.WeatherService.WeatherModels.WeatherDataClass
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ManagerCitiesViewModel @Inject constructor(
    private val citiesUseCase: CitiesUseCase,
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    private val _stateCities = mutableStateOf(CitiesState())
    val stateCities: State<CitiesState> = _stateCities

    var allWeather = mutableStateOf<List<CurrentCityWeather>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var getCitiesJob: Job? = null

    init {
        getCities()
    }

    private fun getCities() {
        getCitiesJob?.cancel()
        getCitiesJob = citiesUseCase
            .getCities()
            .onEach { cities ->
                allWeather.value = emptyList()
                cities.sortedBy { it.id }.forEach { city ->
                    val weatherData = Gson().fromJson(city.weatherData, WeatherDataClass::class.java)
                    Log.e("getGsonData", weatherData.toString())
                    Log.e("myTime", (System.currentTimeMillis() / 1000).toString())
                    if (((System.currentTimeMillis() / 1000) - weatherData.now) > 1800) {
                        Log.e(
                            "update",
                            "${city.name} time left: ${(
                                    (System.currentTimeMillis() / 1000) -
                                            weatherData.now
                                    ) / 60}min"
                        )
                        city.id?.let {
                            weatherUseCase.getWeather(lat = city.lat, lon = city.lon).onEach { result ->
                                when (result) {
                                    is Resource.Success -> {
                                        result.data?.let {
                                            Log.e("success updateCity", city.name)
                                            citiesUseCase.updateCity(
                                                City(
                                                    id = city.id,
                                                    name = city.name,
                                                    lat = city.lat,
                                                    lon = city.lon,
                                                    icon = result.data.fact.condition,
                                                    tempNow = result.data.fact.temp.toString(),
                                                    weatherData = Gson().toJson(result.data)
                                                )
                                            )
                                        }
                                        loadError.value = ""
                                        isLoading.value = false
                                    }
                                    is Resource.Loading -> {
                                        Log.e("Loading", "true")
                                        loadError.value = ""
                                        isLoading.value = true
                                    }
                                    is Resource.Error -> {
                                        Log.e("error update", result.message!!)
                                        loadError.value = result.message
                                        isLoading.value = false
                                    }
                                }
                            }.launchIn(viewModelScope)
                        }
                    } else {
                        Log.e(
                            "no update",
                            "${city.name} time left: ${(
                                    (System.currentTimeMillis() / 1000) -
                                            weatherData.now
                                    ) / 60}min"
                        )
                        allWeather.value += CurrentCityWeather(
                            id = city.id!!,
                            name = city.name,
                            icon = weatherData.fact.condition,
                            lat = city.lat,
                            lon = city.lon,
                            tempNow = weatherData.fact.temp.toString(),
                            weatherData = weatherData
                        )
                    }
                }
                _stateCities.value = stateCities.value.copy(
                    cities = cities
                )
            }
            .launchIn(viewModelScope)
    }

    sealed class UIEvent {
        object ClickOnItem : UIEvent()
        object AddNewLocation : UIEvent()
    }
}
