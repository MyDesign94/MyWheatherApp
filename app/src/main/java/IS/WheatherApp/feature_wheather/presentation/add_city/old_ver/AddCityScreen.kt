package IS.WheatherApp.feature_wheather.presentation.add_city

import IS.WheatherApp.feature_wheather.presentation.add_city.component.TransparentHintTextField
import IS.WheatherApp.feature_wheather.presentation.add_city.old_ver.AddCityViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddCityScreen(
    navController: NavController,
    viewModel: AddCityViewModel = hiltViewModel()
) {
    val latState = viewModel.cityLatitude.value
    val lonState = viewModel.cityLongitude.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddCityViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddCityViewModel.UiEvent.SaveCity -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddCityEvent.SaveCity)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = latState.text,
                hint = latState.hint,
                onValueChange = {
                    viewModel.onEvent(AddCityEvent.EnteredLat(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddCityEvent.ChangeLatFocus(it))
                },
                isHintVisible = latState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = lonState.text,
                hint = lonState.hint,
                onValueChange = {
                    viewModel.onEvent(AddCityEvent.EnteredLon(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddCityEvent.ChangeLonFocus(it))
                },
                isHintVisible = lonState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
