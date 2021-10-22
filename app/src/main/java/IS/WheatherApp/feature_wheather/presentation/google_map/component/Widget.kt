package IS.WheatherApp.feature_wheather.presentation.google_map.component

import IS.WheatherApp.R
import IS.WheatherApp.feature_wheather.presentation.google_map.GoogleMapPointerState
import IS.WheatherApp.feature_wheather.presentation.google_map.MapPointerMovingState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedMapPointer(
    modifier: Modifier = Modifier,
    buttonState: MutableState<MapPointerMovingState> = mutableStateOf(MapPointerMovingState.IDLE),
    pointerState: GoogleMapPointerState
) {
}

@Composable
fun MapPointer(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.Gray.copy(alpha = .3f), CircleShape)
                .wrapContentWidth()
        )
        Image(
            painter = painterResource(R.drawable.ic_location_pointer_origin),
            modifier = Modifier
                .size(32.dp, 64.dp),
            contentDescription = null
        )
    }
}

@Composable
fun YourAddress(
    modifier: Modifier,
    address: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your address>",
            style = MaterialTheme.typography.subtitle2,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Text(
            text = address,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
