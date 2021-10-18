package IS.WheatherApp.feature_wheather.presentation.add_city.old_ver

import IS.WheatherApp.feature_wheather.domain.model.City
import IS.WheatherApp.feature_wheather.domain.model.InvalidCityItemException
import IS.WheatherApp.feature_wheather.domain.use_case.db_use_case.CitiesUseCase
import IS.WheatherApp.feature_wheather.domain.use_case.weather_use_case.WeatherUseCase
import IS.WheatherApp.feature_wheather.domain.util.Resource
import IS.WheatherApp.feature_wheather.presentation.add_city.AddCityEvent
import android.content.Context
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCityViewModel @Inject constructor(
    private val useCase: CitiesUseCase,
    private val weatherUseCase: WeatherUseCase,
    private val context: Context
) : ViewModel() {

    private val _cityLatitude = mutableStateOf(
        AddCityTextFieldState(
            hint = "Enter Latitude…"
        )
    )
    val cityLatitude: State<AddCityTextFieldState> = _cityLatitude

    private val _cityLongitude = mutableStateOf(
        AddCityTextFieldState(
            hint = "Enter Longitude…"
        )
    )
    val cityLongitude: State<AddCityTextFieldState> = _cityLongitude

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentCityId: Int? = null

    fun onEvent(event: AddCityEvent) {
        when (event) {
            is AddCityEvent.EnteredLat -> {
                _cityLatitude.value = cityLatitude.value.copy(
                    text = event.value
                )
            }
            is AddCityEvent.ChangeLatFocus -> {
                _cityLatitude.value = cityLatitude.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                        cityLatitude.value.text.isBlank()
                )
            }
            is AddCityEvent.EnteredLon -> {
                _cityLongitude.value = cityLongitude.value.copy(
                    text = event.value
                )
            }
            is AddCityEvent.ChangeLonFocus -> {
                _cityLongitude.value = cityLongitude.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                        cityLongitude.value.text.isBlank()
                )
            }
            is AddCityEvent.SaveCity -> {
                viewModelScope.launch {
                    try {
                        weatherUseCase.getWeather(
                            lat = cityLatitude.value.text,
                            lon = cityLongitude.value.text
                        ).onEach { result ->
                            when (result) {
                                is Resource.Success -> {
                                    useCase.addCity(
                                        City(
                                            lat = cityLatitude.value.text,
                                            lon = cityLongitude.value.text,
                                            id = currentCityId,
                                            name = getAddress(
                                                cityLatitude.value.text.toDouble(),
                                                cityLongitude.value.text.toDouble()
                                            ),
                                            tempNow = result.data?.fact?.temp.toString(),
                                            icon = result.data?.fact?.condition.toString()
                                        )
                                    )
                                    Log.e("add_city_vm_success", " true")
                                    _eventFlow.emit(UiEvent.SaveCity)
                                }
                                is Resource.Error -> {
                                    _eventFlow.emit(
                                        UiEvent.ShowSnackbar(
                                            message = result.message
                                                ?: "An unexpected error occured"
                                        )
                                    )
                                    Log.e("add_city_vm_error", " true")
                                }
                                is Resource.Loading -> {
                                    Log.e("add_city_vm_loading", " true")
                                }
                            }
                        }.launchIn(viewModelScope)
                    } catch (e: InvalidCityItemException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save city"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun getAddress(lat: Double, lon: Double): String {
        val geocoder = Geocoder(context)
        val list = geocoder.getFromLocation(lat, lon, 1)
        if (list[0].locality == null) {
            return list[0].countryName
        }
        return "${list[0].countryName}, ${list[0].locality}"
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveCity : UiEvent()
    }
}
