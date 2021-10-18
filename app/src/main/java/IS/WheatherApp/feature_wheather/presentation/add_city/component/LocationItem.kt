package IS.WheatherApp.feature_wheather.presentation.add_city.component

import IS.WheatherApp.feature_wheather.domain.util.Screen
import IS.WheatherApp.feature_wheather.domain.util.StandardLocation
import IS.WheatherApp.feature_wheather.presentation.ui.theme.BackgroundCardColor
import IS.WheatherApp.feature_wheather.presentation.ui.theme.NxtDays
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson

@ExperimentalMaterialApi
@Composable
fun LocationItem(
    data: StandardLocation,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(45.dp)
            .clip(CircleShape)
            .background(BackgroundCardColor)
            .clickable {
                val dataJson = Gson().toJson(data)
                navController.navigate(
                    Screen.ResultAddNewLocation.route + "?data=$dataJson"
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = data.name,
            style = MaterialTheme.typography.subtitle2,
            maxLines = 1,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun MyLocation(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(45.dp)
            .clip(CircleShape)
            .background(BackgroundCardColor)
            .clickable {
                val dataJson = Gson().toJson(
                    StandardLocation(
                        name = "Locate",
                        lat = -1.0,
                        lon = -1.0
                    )
                )
                navController.navigate(
                    Screen.ResultAddNewLocation.route +
                        "?data=$dataJson"
                )
            }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null,
                tint = NxtDays
            )
            Text(
                text = "Locate",
                style = MaterialTheme.typography.subtitle2,
                maxLines = 1,
                textAlign = TextAlign.Center,
                color = NxtDays,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}
