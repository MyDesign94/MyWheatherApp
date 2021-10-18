package IS.WheatherApp.feature_wheather.presentation.manager_cities.component

import IS.WheatherApp.feature_wheather.domain.model.weather_model.CurrentCityWeather
import IS.WheatherApp.feature_wheather.presentation.main_wheather.component.iconWeather
import IS.WheatherApp.feature_wheather.presentation.ui.theme.BackgroundCardColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CitiesItem(
    city: CurrentCityWeather,
    onItemClick: (CurrentCityWeather) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp, bottom = 10.dp)
        .clip(shape = RoundedCornerShape(15.dp))
        .background(BackgroundCardColor)
        .wrapContentSize(Alignment.Center)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(city) }
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = iconWeather(city.icon, city.weatherData!!.fact.daytime)),
                contentDescription = "city_icon_weather",
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(35.dp)
            )
            Text(
                text = city.name,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f),
                textAlign = TextAlign.Start
            )
            Text(text = "${city.tempNow}℃", style = MaterialTheme.typography.h6)
        }
    }
}


