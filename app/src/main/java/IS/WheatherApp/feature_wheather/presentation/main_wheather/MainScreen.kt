package IS.WheatherApp.feature_wheather.presentation.main_wheather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import IS.WheatherApp.feature_wheather.domain.util.Screen
import IS.WheatherApp.feature_wheather.domain.util.TimePeriod
import IS.WheatherApp.feature_wheather.domain.util.collectionsOfItemsRow
import IS.WheatherApp.feature_wheather.presentation.main_wheather.component.iconWeather
import IS.WheatherApp.feature_wheather.presentation.main_wheather.component.iconWeatherForTime
import IS.WheatherApp.feature_wheather.presentation.ui.theme.BackgroundCardColor
import IS.WheatherApp.feature_wheather.presentation.ui.theme.NxtDays
import IS.WheatherApp.feature_wheather.presentation.ui.theme.TextColor3
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShortText
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.WeatherService.WeatherModels.BackLayerData
import com.example.weatherapp.WeatherService.WeatherModels.FrontLayerData
import com.example.weatherapp.WeatherService.WeatherModels.RowTimeWeatherItems
import com.example.weatherapp.WeatherService.WeatherModels.WeatherDataClass
import com.google.gson.Gson

@ExperimentalMaterialApi
@Composable
fun MainWeatherScreen(
    navController: NavController,
    viewModel: MainWeatherViewModel = hiltViewModel()
) {
    val scaffoldState: BackdropScaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed)
    val state = viewModel.state.value
    val timePeriod = viewModel.timePeriod.value
    Box(modifier = Modifier.fillMaxSize()) {
        state.weatherData?.let {
//            Image(
//                painter = painterResource(id = R.drawable.bg_photo),
//                contentDescription = "spb_back",
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
            Column(modifier = Modifier
                .fillMaxSize()
            ) {
                BackdropScaffold(
                    appBar = {
                        TopAppBar(
                            title = {
                                Text(text = "")
                            },
                            navigationIcon = {
                                Icon(
                                    Icons.Filled.ArrowBack,
                                    contentDescription = "navigation",
                                    tint = Color.White,
                                    modifier = Modifier.clickable {
                                        navController.navigate(Screen.ManagerCitiesScreen.route)
                                    }
                                )
                            },
                            modifier = Modifier
                                .padding(top = 40.dp, end = 20.dp)
                                .height(30.dp),
                            actions = {
                                Icon(
                                    Icons.Filled.ShortText,
                                    contentDescription = "settings",
                                    tint = Color.White,
                                    modifier = Modifier.clickable {  }
                                )
                            },
                            backgroundColor = Color.Transparent,
                            elevation = 0.dp
                        )
                    },
                    backLayerContent = {
                        if (timePeriod == TimePeriod.ToDay) {
                            BackLayerWeather(
                                cityId = state.cityId,
                                navController = navController,
                                timePeriod = timePeriod,
                                weatherData = state.weatherData,
                                basicWeatherData = BackLayerData(
                                    temp = state.weatherData.fact.temp.toString(),
                                    feelsLike = state.weatherData.fact.feels_like.toString(),
                                    condition = state.weatherData.fact.condition)
                            )
                        } else {
                            BackLayerWeather(
                                timePeriod = timePeriod,
                                weatherData = state.weatherData,
                                basicWeatherData = BackLayerData(
                                    temp = state.weatherData.forecasts[1].parts.day.temp_avg.toString(),
                                    feelsLike = state.weatherData.forecasts[1].parts.day.feels_like.toString(),
                                    condition = state.weatherData.forecasts[1].parts.day.condition),
                                navController = navController,
                                cityId = state.cityId
                            )
                        }
                    },
                    frontLayerContent = {
                        if (timePeriod == TimePeriod.ToDay) {
                            DetailedInformation(data = FrontLayerData(
                                sunrise = state.weatherData.forecasts[0].sunrise,
                                sunset = state.weatherData.forecasts[0].sunset,
                                precipitation = state.weatherData.forecasts[0].parts.day.prec_prob.toString(),
                                humidity =state.weatherData.fact.humidity.toString(),
                                wind = state.weatherData.fact.wind_speed.toString(),
                                pressure = state.weatherData.fact.pressure_mm.toString())
                            )
                        } else {
                            DetailedInformation(data = FrontLayerData(
                                sunrise = state.weatherData.forecasts[1].sunrise,
                                sunset = state.weatherData.forecasts[1].sunset,
                                precipitation = state.weatherData.forecasts[1].parts.day.prec_prob.toString(),
                                humidity = state.weatherData.forecasts[1].parts.day.humidity.toString(),
                                wind = state.weatherData.forecasts[1].parts.day.wind_speed.toString(),
                                pressure = state.weatherData.forecasts[1].parts.day.pressure_mm.toString())
                            )
                        }
                    },
                    backLayerBackgroundColor = Color.Transparent,
                    frontLayerBackgroundColor = Color.Transparent,
                    frontLayerShape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
                    scaffoldState = scaffoldState,
                    peekHeight = 340.dp,
                    backLayerContentColor = Color.White,
                    frontLayerScrimColor = Color.Transparent,
                    stickyFrontLayer = false
                )
            }
        }
    }
}

@Composable
private fun BackLayerWeather(
    timePeriod: TimePeriod,
    weatherData: WeatherDataClass?,
    viewModel: MainWeatherViewModel = hiltViewModel(),
    basicWeatherData: BackLayerData,
    navController: NavController,
    cityId: Int
) {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (basicInf, detailedInf, spacer) = createRefs()
        BasicInformation(modifier = Modifier.constrainAs(basicInf){
            top.linkTo(parent.top)
        })
        Spacer(modifier = Modifier
            .defaultMinSize(40.dp, 20.dp)
            .constrainAs(spacer) {
                top.linkTo(basicInf.bottom)
            })
        DetailedInformationInTime(
            cityId = cityId,
            navController = navController,
            timePeriod = timePeriod,
            weatherData = weatherData,
            modifier = Modifier.constrainAs(detailedInf){
                bottom.linkTo(parent.bottom, margin = 40.dp)
            },
        )
    }
}

@Composable
fun BasicInformation(
    modifier: Modifier = Modifier,
    viewModel: MainWeatherViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    state.weatherData?.let { weatherDataClass->
        Column(modifier = modifier) {
            Text(
                text = state.name,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4
            )
            Text(
                text = weatherDataClass.fact.condition,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = weatherDataClass.fact.temp.toString(),
                    style = MaterialTheme.typography.h1
                )
                Text(
                    text = "℃",
                    modifier = Modifier.padding(top = 18.dp),
                    style = MaterialTheme.typography.h3
                )
            }
            Text(
                text = "feels like ${weatherDataClass.fact.feels_like}℃",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
fun DetailedInformationInTime(
    modifier: Modifier = Modifier,
    timePeriod: TimePeriod,
    weatherData: WeatherDataClass?,
    viewModel: MainWeatherViewModel = hiltViewModel(),
    navController: NavController,
    cityId: Int
) {
    if (timePeriod == TimePeriod.ToDay) {
        Column(modifier = modifier) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = { viewModel.onTimePeriodStateChange(TimePeriod.ToDay) },
                    modifier = Modifier.padding(start = 15.dp)
                ) {
                    Text(
                        text = "ToDay",
                        color = Color.White
                    )
                }
                TextButton(
                    onClick = { viewModel.onTimePeriodStateChange(TimePeriod.Tomorrow) },
                    modifier = Modifier.padding(start = 40.dp)
                ) {
                    Text(
                        text = "Tomorrow",
                        color = Color(0x61FFFFFF)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = {
                    navController.navigate(
                        Screen.WeatherNextSevenDays.route + "?cityId=${viewModel.state.value.cityId}"
                    )
                })
                {
                    Text(
                        text = "Nex 7 days >",
                        color = NxtDays
                    )
                }


            }
            Divider(color = Color.White, thickness = 1.dp)
            RowOfItemWeatherToTime(collectionsOfItemsRow(weatherData))
        }
    } else {
        Column(modifier = modifier) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = { viewModel.onTimePeriodStateChange(TimePeriod.ToDay) },
                    modifier = Modifier.padding(start = 15.dp)
                ) {
                    Text(
                        text = "ToDay",
                        color = Color(0x61FFFFFF)
                    )
                }
                TextButton(
                    onClick = { viewModel.onTimePeriodStateChange(TimePeriod.Tomorrow) },
                    modifier = Modifier
                        .padding(start = 40.dp)
                ) {
                    Text(
                        text = "Tomorrow",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = {
                    val dataJson = Gson().toJson(viewModel.state.value)
                    navController.navigate(
                        Screen.WeatherNextSevenDays.route + "?data=${dataJson}"
                        //We have two options
                        //1 - pass only cityId and receive all data through the weather service
                        //2 - pass all required parameters for this screen to Json
                    )
                })
                {
                    Text(
                        text = "Nex 7 days >",
                        color = NxtDays
                    )
                }
            }
            Divider(color = Color.White, thickness = 1.dp)
            RowOfItemWeatherToTime(collectionsOfItemsRow(weatherData, dayTime = 1))
        }
    }
}

@Composable
private fun RowOfItemWeatherToTime(
    weatherData: MutableList<RowTimeWeatherItems>
) {
    LazyRow {
        items(weatherData) { item ->
            Column(
                modifier = Modifier
                    .padding(top = 20.dp, start = 15.dp, end = 15.dp)
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
            ) {
                Text(
                    text = item.time.toString() + " h.",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .height(90.dp)
                        .width(50.dp)
                        .clip(CircleShape)
                        .background(BackgroundCardColor)
                        .wrapContentSize(Alignment.Center),
                ) {
                    Column(
                        modifier = Modifier.wrapContentSize(Alignment.Center)
                    ) {
                        Image(
                            painter = painterResource(
                                id = iconWeatherForTime(item.weather, item.time)
                            ),
                            contentDescription = "Icon weather",
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(25.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${item.temperature}℃",
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                    }
                }

            }
        }

    }
}

@Composable
fun DetailedInformation(
    data: FrontLayerData
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
            .background(NxtDays)
            .wrapContentSize(Alignment.TopCenter)
    ) {
        Column(modifier = Modifier
            .padding(top = 10.dp, start = 20.dp, end = 20.dp)
            .wrapContentSize(Alignment.TopCenter)
        ) {
            Box(modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Line(modifier = Modifier.width(50.dp))
                    Spacer(modifier = Modifier.height(2.dp))
                    Line(modifier = Modifier.width(40.dp))
                }
            }
            RowOfDetaledInfo(
                leftTitle = "SUNRISE", leftData = data.sunrise, leftPrefix = "am",
                rightTitle = "SUNSET", rightData = data.sunset, rightPrefix = "pm"
            )
            Divider(color = Color.LightGray, thickness = 1.dp)
            RowOfDetaledInfo(
                leftTitle = "PRECIPITATION", leftData = data.precipitation, leftPrefix = "%",
                rightTitle = "HUMIDITY", rightData = data.humidity, rightPrefix = "%"
            )
            Divider(color = Color.LightGray, thickness = 1.dp)
            RowOfDetaledInfo(
                leftTitle = "WIND", leftData = data.wind, leftPrefix = "m/s",
                rightTitle = "PRESSURE", rightData = data.pressure, rightPrefix = "mm"
            )
        }
    }
}

@Composable
fun RowOfDetaledInfo(
    leftTitle: String,
    leftData: String,
    leftPrefix: String,
    rightTitle: String,
    rightData: String,
    rightPrefix: String
) {
    Row(
        modifier = Modifier
            .padding(bottom = 30.dp, top = 25.dp)
            .fillMaxWidth()
    ) {
        DetailedCardItem(
            textItem = leftTitle, meaning = leftData, prefix = leftPrefix,
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start),
            contentModifier = Modifier.wrapContentWidth(Alignment.Start)
        )
        DetailedCardItem(
            textItem = rightTitle, meaning = rightData, prefix = rightPrefix,
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End),
            contentModifier = Modifier.wrapContentWidth(Alignment.Start)
        )
    }
}

@Composable
fun DetailedCardItem(
    textItem: String, meaning: String, prefix: String,
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier){
    Column(modifier = modifier
        .width(150.dp)
    ) {
        Column(modifier = contentModifier) {
            Text(
                text = textItem,
                color = TextColor3,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = meaning + prefix,
//                color = DetailedText,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Start
            )
        }

    }
}

@Composable
fun Line(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(3.dp)
            .background(Color.White)
            .clip(shape = RoundedCornerShape(3.dp))
    )
}