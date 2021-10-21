package IS.WheatherApp.feature_wheather.presentation.google_map

import IS.WheatherApp.feature_wheather.domain.util.APP_ACTIVITY
import IS.WheatherApp.feature_wheather.domain.util.LocationLiveData
import android.content.Context
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

    init {
        locationData.observe(APP_ACTIVITY, {
            _pointerLocation.value = pointerLocation.value.copy(
                latLng = LatLng(it.latitude, it.longitude)
            )
        })
    }
}
