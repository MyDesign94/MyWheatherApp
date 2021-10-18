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
): ViewModel() {

    private val _stateCities = mutableStateOf(CitiesState())
    val stateCities: State<CitiesState> = _stateCities

    var allWeather = mutableStateOf<List<CurrentCityWeather>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var getCitiesJob: Job? = null

    init {
        getCities()
    }

    private fun getWeather(lat: String, lon: String, id: Int, name: String) {
        Log.e("manager_city_vm_data_gps", "$lat - $lon - $id")
        weatherUseCase.getWeather(lat = lat, lon = lon).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    result.data?.let {
                        allWeather.value += CurrentCityWeather(
                            id = id,
                            name = name,
                            icon = result.data.fact.condition,
                            lat = lat,
                            lon = lon,
                            tempNow = result.data.fact.temp.toString(),
                            weatherData = result.data
                        )
                        Log.e("allWeather.vale", allWeather.value.size.toString())
                        citiesUseCase.updateCity(City(
                            id = id,
                            name = name,
                            lat = lat,
                            lon = lon,
                            icon = result.data.fact.condition,
                            tempNow = result.data.fact.temp.toString(),
                            weatherData = Gson().toJson(result.data)
                        ))
                    }
                    loadError.value = ""
                    isLoading.value = false
                }
                is Resource.Loading -> {
                    loadError.value = ""
                    isLoading.value = true
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCities() {
        getCitiesJob?.cancel()
        allWeather.value = listOf()
        getCitiesJob = citiesUseCase
            .getCities()
            .onEach { cities ->
                cities.sortedBy { it.id }.forEach { city ->
                    val weatherData = Gson().fromJson(city.weatherData, WeatherDataClass::class.java)
                    Log.e("getGsonData", weatherData.toString())
                    Log.e("myTime", (System.currentTimeMillis()/1000).toString())
                    if (((System.currentTimeMillis()/1000) - weatherData.now) > 1800) {
                        city.id?.let { getWeather(lat = city.lat, lon = city.lon, id = it, name = city.name) }
                    } else {
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

    override fun onCleared() {
        super.onCleared()
        allWeather.value = listOf()
    }
}