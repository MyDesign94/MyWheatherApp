package IS.WheatherApp.feature_wheather.presentation.manager_cities

import IS.WheatherApp.feature_wheather.domain.model.weather_model.CurrentCityWeather
import IS.WheatherApp.feature_wheather.domain.util.Screen
import IS.WheatherApp.feature_wheather.presentation.manager_cities.component.CitiesItem
import IS.WheatherApp.feature_wheather.presentation.ui.theme.NxtDays
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ManagerCitiesScreen(
    navController: NavController,
    viewModel: ManagerCitiesViewModel = hiltViewModel()
) {
    val cities = viewModel.allWeather.value
    ManagerCities(navController = navController, cities = cities)

}

@Composable
fun Loading(){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(46.dp),
            strokeWidth = 4.dp,
            color = Color.White
        )
    }
}

@Composable
fun ManagerCities(
    navController: NavController,
    cities: List<CurrentCityWeather>
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddCityScreen.route)
                },
                backgroundColor = NxtDays
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Manage cities",
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(cities) { city ->
                    CitiesItem(city = city, onItemClick = {
                        navController.navigate(
                            Screen.MainWeatherScreen.route +
                                    "?cityId=${city.id}"
                        )
                    })
                }
            }
        }
    }
}
