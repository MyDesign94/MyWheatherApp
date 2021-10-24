package IS.WheatherApp.feature_wheather.presentation.google_map

import com.google.android.libraries.maps.model.LatLng

sealed class GoogleMapEvent {
    data class PointerAnimateStateChange(val value: MapPointerMovingState) : GoogleMapEvent()
    data class ZoomLevel(val value: Float) : GoogleMapEvent()
    data class PointerState(val value: LatLng) : GoogleMapEvent()
    object UpdateLocationUI : GoogleMapEvent()
    object SaveLocation : GoogleMapEvent()
}
