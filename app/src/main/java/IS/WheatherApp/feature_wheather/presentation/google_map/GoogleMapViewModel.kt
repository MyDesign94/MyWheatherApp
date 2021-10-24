package IS.WheatherApp.feature_wheather.presentation.google_map

import IS.WheatherApp.BuildConfig
import IS.WheatherApp.feature_wheather.domain.model.location_model.StandardLocation
import IS.WheatherApp.feature_wheather.domain.util.APP_ACTIVITY
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoogleMapViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {

    private val _pointerLocation = mutableStateOf(GoogleMapPointerState())
    val pointerLocation: State<GoogleMapPointerState> = _pointerLocation

    private val _pointerAnimationState = mutableStateOf(MapPointerMovingState.DRAGGING)
    val pointerAnimationState: State<MapPointerMovingState> = _pointerAnimationState

    private val _address = mutableStateOf("checking...")
    val address: State<String> = _address

    private val _zoomLevel = mutableStateOf(17f)
    val zoomLevel: State<Float> = _zoomLevel

    private val _data = mutableStateOf("")
    val data: State<String> = _data

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var placesClient: PlacesClient
    private var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null

    init {
        Places.initialize(context, BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(APP_ACTIVITY)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(APP_ACTIVITY)
        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()
    }

    fun onEvent(event: GoogleMapEvent) {
        when (event) {
            is GoogleMapEvent.PointerAnimateStateChange -> {
                onPointerAnimationStateChange(event.value)
                Log.e("PASC_Event", event.value.toString())
            }
            is GoogleMapEvent.PointerState -> {
                getAddress(event.value)
                setPointerState(event.value)
            }
            is GoogleMapEvent.ZoomLevel -> {
                setZoomLevel(event.value)
            }
            is GoogleMapEvent.UpdateLocationUI -> {
                updateLocationUI()
            }
            is GoogleMapEvent.SaveLocation -> {
                viewModelScope.launch {
                    _data.value = Gson().toJson(
                        StandardLocation(
                            name = address.value,
                            lat = pointerLocation.value.latLng.latitude,
                            lon = pointerLocation.value.latLng.longitude
                        )
                    )
                    _eventFlow.emit(UiEvent.SaveLocation)
                }
            }
        }
    }

    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(APP_ACTIVITY) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        _pointerLocation.value = pointerLocation.value.copy(
                            latLng = LatLng(task.result.latitude, task.result.longitude)
                        )
                        Log.e("DeviceLocation", lastKnownLocation.toString())
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun updateLocationUI() {
        try {
            if (locationPermissionGranted) {
                getDeviceLocation()
                _zoomLevel.value = 17f
            } else {
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                APP_ACTIVITY,
                ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                APP_ACTIVITY, arrayOf(ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    private fun getAddress(latLng: LatLng) {
        address(latLng).let { address ->
            if (address?.locality == null) {
                _address.value = address?.adminArea ?: "Cant find location"
            } else {
                _address.value = address.locality ?: "Cant find location"
            }
        }
    }

    private fun onPointerAnimationStateChange(newState: MapPointerMovingState) {
        _pointerAnimationState.value = newState
    }

    private fun setZoomLevel(zoomLevel: Float) {
        _zoomLevel.value = zoomLevel
    }

    private fun setPointerState(latLng: LatLng) {
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
            UiEvent.ShowSnackbar(message = e.message ?: "Cant find location")
            null
        }
    }

    companion object {
        private val TAG = GoogleMapViewModel::class.java.simpleName
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveLocation : UiEvent()
    }
}
