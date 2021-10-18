package IS.WheatherApp.feature_wheather.presentation.nex_seven_days

import IS.WheatherApp.feature_wheather.domain.util.getListOfDaysOfWeek
import IS.WheatherApp.feature_wheather.presentation.result_add_new_location.component.WeekItem
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun NextSevenDaysScreen(
    navController: NavController,
    viewModel: NextSevenDaysViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.name)
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                state.weatherData?.let { data ->
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
            }
        }
    }
}
