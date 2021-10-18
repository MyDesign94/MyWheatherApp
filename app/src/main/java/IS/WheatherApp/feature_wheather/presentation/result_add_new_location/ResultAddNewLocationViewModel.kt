package IS.WheatherApp.feature_wheather.presentation.result_add_new_location

import IS.WheatherApp.feature_wheather.domain.model.City
import IS.WheatherApp.feature_wheather.domain.model.InvalidCityItemException
import IS.WheatherApp.feature_wheather.domain.use_case.db_use_case.CitiesUseCase
import IS.WheatherApp.feature_wheather.domain.use_case.weather_use_case.WeatherUseCase
import IS.WheatherApp.feature_wheather.domain.util.APP_ACTIVITY
import IS.WheatherApp.feature_wheather.domain.util.LocationLiveData
import IS.WheatherApp.feature_wheather.domain.util.Resource
import IS.WheatherApp.feature_wheather.domain.util.StandardLocation
import IS.WheatherApp.feature_wheather.presentation.main_wheather.MainWeatherState
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultAddNewLocationViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val citiesUseCase: CitiesUseCase,
    private val context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val locationData = LocationLiveData(context)

    private val _state = mutableStateOf(ResultState())
    val state: State<ResultState> = _state

    private val _stateWeather = mutableStateOf(MainWeatherState())
    val stateWeather: State<MainWeatherState> = _stateWeather

    private val _eventFlow = MutableSharedFlow<ResultAddNewLocationViewModel.UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentCityId: Int? = null

    init {
        savedStateHandle.get<String>("data")?.let { json ->
            val data = Gson().fromJson(json, StandardLocation::class.java)
            if (data.name != "Locate") {
                Log.e("data_name", data.name)
                _state.value = state.value.copy(
                    data = data
                )
                getWeather(lat = data.lat.toString(), lon = data.lon.toString())
            } else {
                Log.e("data_name", data.name)
                locationData.observe(APP_ACTIVITY, {
                    _state.value = state.value.copy(
                        data = StandardLocation(
                            name = getAddress(it.latitude, it.longitude),
                            lat = it.latitude,
                            lon = it.longitude
                        )
                    )
                    getWeather(lat = state.value.data?.lat.toString(), lon = state.value.data?.lon.toString())
                    Log.e("location", it.toString())
                })
            }
        }
    }

    private fun getWeather(lat: String, lon: String) {
        weatherUseCase.getWeather(lat = lat, lon = lon).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateWeather.value = MainWeatherState(weatherData = result.data)
                    Log.e("data", result.data.toString())
                }
                is Resource.Error -> {
                    _stateWeather.value = MainWeatherState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _stateWeather.value = MainWeatherState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent() {
        viewModelScope.launch {
            try {
                val weatherData = Gson().toJson(stateWeather.value.weatherData)
                Log.e("insert Gson", weatherData)
                citiesUseCase.addCity(
                    City(
                        lat = state.value.data?.lat.toString(),
                        lon = state.value.data?.lat.toString(),
                        id = currentCityId,
                        name = getAddress(
                            lat = state.value.data!!.lat,
                            lon = state.value.data!!.lon
                        ),
                        tempNow = stateWeather.value.weatherData!!.fact.temp.toString(),
                        icon = stateWeather.value.weatherData!!.fact.condition,
                        weatherData = Gson().toJson(stateWeather.value.weatherData)
                    )
                )
                _eventFlow.emit(UiEvent.SaveCity)
            } catch (e: InvalidCityItemException) {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        message = e.message ?: "Couldn't save city"
                    )
                )
            }
        }
    }

    private fun getAddress(lat: Double, lon: Double): String {
        val geocoder = Geocoder(context)
        val list = geocoder.getFromLocation(lat, lon, 1)
        if (list[0].locality == null) {
            return list[0].countryName
        }
        return "${list[0].countryCode}, ${list[0].locality}"
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveCity : UiEvent()
    }
}
