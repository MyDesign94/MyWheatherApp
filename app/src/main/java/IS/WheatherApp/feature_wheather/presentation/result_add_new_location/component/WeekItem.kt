package IS.WheatherApp.feature_wheather.presentation.result_add_new_location.component

import IS.WheatherApp.R
import IS.WheatherApp.feature_wheather.presentation.main_wheather.component.iconWeather
import IS.WheatherApp.feature_wheather.presentation.ui.theme.BackgroundCardColor
import IS.WheatherApp.feature_wheather.presentation.ui.theme.TextColor3
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun WeekItem(
    dayOfWeek: String,
    day: String,
    weatherDay: Int,
    weatherNight: Int,
    icon: String
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp)
        .clip(shape = RoundedCornerShape(15.dp))
        .background(BackgroundCardColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = iconWeather(icon)),
                contentDescription = "city_icon_weather",
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(35.dp)
            )
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = dayOfWeek,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = day,
                    style = MaterialTheme.typography.body2
                )
            }
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "${weatherDay}℃",
                    style = MaterialTheme.typography.h6,
                )
                Text(
                    text = "${weatherNight}℃",
                    style = MaterialTheme.typography.h6,
                    color = TextColor3
                )
            }
        }
    }

}

