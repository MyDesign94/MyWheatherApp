package IS.WheatherApp.feature_wheather.presentation.add_city

import androidx.compose.ui.focus.FocusState

sealed class AddCityEvent {
    data class EnteredLat(val value: String): AddCityEvent()
    data class EnteredCity(val value: String): AddCityEvent()
    data class ChangeLatFocus(val focusState: FocusState): AddCityEvent()
    data class EnteredLon(val value: String): AddCityEvent()
    data class ChangeLonFocus(val focusState: FocusState): AddCityEvent()
    object SaveCity: AddCityEvent()
}
