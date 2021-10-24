package IS.WheatherApp.feature_wheather.presentation.google_map.component

import IS.WheatherApp.R
import IS.WheatherApp.feature_wheather.presentation.google_map.MapPointerMovingState
import androidx.compose.animation.core.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedMapPointer(
    modifier: Modifier = Modifier,
    pointerAnimationState: MapPointerMovingState = MapPointerMovingState.IDLE
) {
    var circleSizePropState by remember { mutableStateOf(16.dp) }
    var circlePaddingPropState by remember { mutableStateOf(0.dp) }
    var pointerPaddingPropState by remember { mutableStateOf(0.dp) }

    val circleSizeProp by animateDpAsState(
        targetValue = circleSizePropState,
        tween(
            durationMillis = 100
        )
    )
    val circlePaddingProp by animateDpAsState(
        targetValue = circlePaddingPropState,
        tween(
            durationMillis = 100
        )
    )
    val pointerPaddingProp by animateDpAsState(
        targetValue = pointerPaddingPropState,
        tween(
            durationMillis = 100
        )
    )
    if (pointerAnimationState == MapPointerMovingState.DRAGGING) {
        circleSizePropState = 16.dp
        circlePaddingPropState = 0.dp
        pointerPaddingPropState = 0.dp
    } else {
        circleSizePropState = 24.dp
        circlePaddingPropState = 4.dp
        pointerPaddingPropState = 16.dp
    }
    MapPointer(
        modifier = modifier,
        circleSizeProp = circleSizeProp,
        circlePaddingProp = circlePaddingProp,
        pointerPaddingProp = pointerPaddingProp
    )
}

@Composable
fun MapPointer(
    modifier: Modifier = Modifier,
    circleSizeProp: Dp,
    circlePaddingProp: Dp,
    pointerPaddingProp: Dp
) {
    Box(
        modifier = modifier
            .wrapContentWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        Box(
            modifier = Modifier
                .padding(top = circlePaddingProp)
                .size(circleSizeProp)
                .align(Alignment.BottomCenter)
                .background(Color.Gray.copy(alpha = .3f), CircleShape)
                .wrapContentWidth()
        )
        Image(
            painter = painterResource(R.drawable.icon_pointer),
            modifier = Modifier
                .size(36.dp, 68.dp)
                .padding(bottom = pointerPaddingProp),
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
