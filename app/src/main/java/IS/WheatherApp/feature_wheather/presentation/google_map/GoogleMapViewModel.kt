package IS.WheatherApp.feature_wheather.presentation.google_map

import IS.WheatherApp.feature_wheather.domain.util.APP_ACTIVITY
import IS.WheatherApp.feature_wheather.domain.util.LocationLiveData
import IS.WheatherApp.feature_wheather.presentation.add_city.AddNewLocationViewModel
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.libraries.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GoogleMapViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {

    private val locationData = LocationLiveData(context)

    private val _pointerLocation = mutableStateOf(GoogleMapPointerState())
    val pointerLocation: State<GoogleMapPointerState> = _pointerLocation

    private val _buttonState = mutableStateOf(MapPointerMovingState.DRAGGING)
    val buttonState: State<MapPointerMovingState> = _buttonState

    private val _address = mutableStateOf("checking...")
    val address: State<String> = _address

    private val _zoomLevel = mutableStateOf(17f)
    val zoomLevel: State<Float> = _zoomLevel

    fun onButtonStateChange(newState: MapPointerMovingState) {
        _buttonState.value = newState
    }

    init {
        locationData.observe(APP_ACTIVITY, {
            _pointerLocation.value = pointerLocation.value.copy(
                latLng = LatLng(it.latitude, it.longitude)
            )
        })
    }
    fun getAddress(latLng: LatLng) {
        address(latLng).let { address ->
            _address.value = address?.adminArea ?: "Cant find location"
        }
    }

    fun setZoomLevel(zoomLevel: Float){
        _zoomLevel.value = zoomLevel
    }

    fun setPointerState(latLng: LatLng) {
        _pointerLocation.value = pointerLocation.value.copy(
            latLng = latLng
        )
    }

    private fun address(latLng: LatLng): Address? {
        Log.e("getAddress", "started")
        val geocoder = Geocoder(context)
        return try {
            geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)[0]
        } catch (e: Exception) {
            AddNewLocationViewModel.UiEvent.ShowSnackbar(message = e.message ?: "Cant find location")
            null
        }
    }
}
