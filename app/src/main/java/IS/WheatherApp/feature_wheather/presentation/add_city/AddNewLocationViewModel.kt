package IS.WheatherApp.feature_wheather.presentation.add_city

import IS.WheatherApp.feature_wheather.domain.use_case.db_use_case.CitiesUseCase
import IS.WheatherApp.feature_wheather.domain.use_case.weather_use_case.WeatherUseCase
import IS.WheatherApp.feature_wheather.domain.util.Resource
import IS.WheatherApp.feature_wheather.presentation.main_wheather.MainWeatherState
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddNewLocationViewModel @Inject constructor(
    private val useCase: CitiesUseCase,
    private val weatherUseCase: WeatherUseCase,
    private val context: Context
): ViewModel() {

    private val _searchState = mutableStateOf(String())
    val searchState: State<String> = _searchState

    private val _weatherState = mutableStateOf(MainWeatherState())
    val weatherState: State<MainWeatherState> = _weatherState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private fun getAddress(address: String): Address? {
        val geocoder = Geocoder(context)
        return try {
            geocoder.getFromLocationName(address, 1)[0]
        } catch (e: Exception) {
            UiEvent.ShowSnackbar(message = e.message ?: "Cant find location")
            null
        }
    }

    fun onEvent(event: AddCityEvent) {
        when(event) {
            is AddCityEvent.EnteredCity -> {
                _searchState.value = event.value
                val address = getAddress(event.value)
                if (address != null) {
                    getWeather(address.latitude.toString(), address.longitude.toString(),)
                }
            }
            is AddCityEvent.SaveCity -> {


            }
        }

    }

    private fun getWeather(lat: String, lon: String) {
        Log.e("gps", "${lat} - ${lon}")
        weatherUseCase.getWeather(lat = lat, lon = lon).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _weatherState.value = MainWeatherState(weatherData = result.data)
                    Log.e("data", result.data.toString())
                }
                is Resource.Error -> {
                    _weatherState.value = MainWeatherState(
                        error = result.message?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _weatherState.value = MainWeatherState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveCity: UiEvent()
    }
}