package IS.WheatherApp.feature_wheather.presentation.result_add_new_location

import IS.WheatherApp.feature_wheather.domain.util.Screen
import IS.WheatherApp.feature_wheather.domain.util.getListOfDaysOfWeek
import IS.WheatherApp.feature_wheather.presentation.result_add_new_location.component.WeekItem
import IS.WheatherApp.feature_wheather.presentation.ui.theme.NxtDays
import IS.WheatherApp.feature_wheather.presentation.ui.theme.TextColor2
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ResultAddNewLocation(
    navController: NavController,
    viewModel: ResultAddNewLocationViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val stateWeather = viewModel.stateWeather.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ResultAddNewLocationViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is ResultAddNewLocationViewModel.UiEvent.SaveCity -> {
                    navController.navigate(Screen.ManagerCitiesScreen.route)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.data?.name!!)
                },
                navigationIcon = {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "navigation",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            navController.navigateUp()
                        }
                    )
                },
                modifier = Modifier
                    .padding(top = 40.dp, end = 20.dp)
                    .height(30.dp),
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        },
        scaffoldState = scaffoldState
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            stateWeather.weatherData?.let { data ->
                for (i in 0..6) {
                    item {
                        WeekItem(
                            dayOfWeek = getListOfDaysOfWeek()[i],
                            day = data.forecasts[i].date.substring(5, 10),
                            weatherDay = data.forecasts[i].parts.day.temp_avg,
                            weatherNight = data.forecasts[i].parts.night.temp_avg,
                            icon = data.forecasts[i].parts.day.condition
                        )
                    }
                }
            }
            item {
                FloatingActionButton(
                    onClick = { viewModel.onEvent() },
                    backgroundColor = NxtDays,
                    modifier = Modifier.padding(top = 40.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Save")
                }
            }
            item {
                Text(
                    text = "Add to start page",
                    style = MaterialTheme.typography.subtitle2,
                    color = TextColor2,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
        }
    }
}
