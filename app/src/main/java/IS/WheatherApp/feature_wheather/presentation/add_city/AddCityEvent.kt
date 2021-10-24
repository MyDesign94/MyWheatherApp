package IS.WheatherApp.feature_wheather.presentation.add_city

import IS.WheatherApp.feature_wheather.domain.model.location_model.StandardLocation

sealed class AddCityEvent {
    object GoogleMap : AddCityEvent()
    data class ChosePopularLocation(val value: StandardLocation) : AddCityEvent()
}
