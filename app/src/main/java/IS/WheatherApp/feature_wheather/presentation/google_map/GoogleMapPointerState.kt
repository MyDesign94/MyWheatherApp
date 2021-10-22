package IS.WheatherApp.feature_wheather.presentation.google_map

import com.google.android.libraries.maps.model.LatLng

data class GoogleMapPointerState(
    val latLng: LatLng = LatLng(60.037674, 30.250677)
)

enum class MapPointerMovingState {
    IDLE, DRAGGING
}
