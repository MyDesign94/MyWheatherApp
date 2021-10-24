package IS.WheatherApp.feature_wheather.presentation.add_city

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AddNewLocationViewModel : ViewModel() {

    private val _data = mutableStateOf("")
    val data: State<String> = _data

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: AddCityEvent) {
        viewModelScope.launch {
            when (event) {
                is AddCityEvent.ChosePopularLocation -> {
                    _data.value = Gson().toJson(event.value)
                    _eventFlow.emit(UiEvent.ChosePopularLocation)
                }
                is AddCityEvent.GoogleMap -> {
                    _eventFlow.emit(UiEvent.GoogleMap)
                }
            }
        }
    }

    sealed class UiEvent {
        object GoogleMap : UiEvent()
        object ChosePopularLocation : UiEvent()
    }
}
