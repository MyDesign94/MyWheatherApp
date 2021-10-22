package IS.WheatherApp.feature_wheather.presentation.add_city.component

import IS.WheatherApp.R
import IS.WheatherApp.feature_wheather.presentation.google_map.GoogleMapViewModel
import IS.WheatherApp.feature_wheather.presentation.google_map.MapPointerMovingState
import IS.WheatherApp.feature_wheather.presentation.google_map.component.MapPointer
import IS.WheatherApp.feature_wheather.presentation.google_map.component.YourAddress
import IS.WheatherApp.feature_wheather.presentation.ui.theme.NxtDays
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng

@Composable
fun GoogleMapScreen(
    navController: NavController,
    viewModel: GoogleMapViewModel = hiltViewModel()
) {
    val pointerLocation = viewModel.pointerLocation.value
    val buttonState = remember { mutableStateOf(MapPointerMovingState.DRAGGING) }
    val mapView = rememberMapViewWithLifecycle()
    val zoomLevel = viewModel.zoomLevel.value
    val context = LocalContext.current
    var target = LatLng(60.037674, 30.250677)
    var address = viewModel.address.value

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {
        AndroidView({ mapView }) { mapView ->
            mapView.getMapAsync { map ->
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        pointerLocation.latLng, zoomLevel
                    )
                )
                map.setOnCameraMoveCanceledListener {
                    buttonState.value = MapPointerMovingState.IDLE
                    address = "checking..."
                }
                map.setOnCameraIdleListener {
                    target = map.cameraPosition.target
                    viewModel.setZoomLevel(map.cameraPosition.zoom)
                    viewModel.setPointerState(target)
                    viewModel.getAddress(target)
                    Log.e("getAddress", address)
                    buttonState.value = MapPointerMovingState.DRAGGING
                }
            }
        }
        YourAddress(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 35.dp),
            address = address
        )
        MapPointer(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 52.dp)
        )
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth()
                .height(48.dp),
            onClick = {
                Toast.makeText(context, target.toString(), Toast.LENGTH_LONG).show()
            },
            colors = ButtonDefaults.buttonColors(NxtDays)
        ) {
            Text(
                text = "Save Location",
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5
            )
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }

    // Makes MapView follow the lifecycle of this composable
    val lifecycleObserver = rememberMapLifecycleObserver(mapView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    return mapView
}

@Composable
fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> throw IllegalStateException()
            }
        }
    }
