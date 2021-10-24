package IS.WheatherApp.feature_wheather.presentation.add_city.component

import IS.WheatherApp.R
import IS.WheatherApp.feature_wheather.domain.util.Screen
import IS.WheatherApp.feature_wheather.presentation.google_map.GoogleMapEvent
import IS.WheatherApp.feature_wheather.presentation.google_map.GoogleMapViewModel
import IS.WheatherApp.feature_wheather.presentation.google_map.MapPointerMovingState
import IS.WheatherApp.feature_wheather.presentation.google_map.component.AnimatedMapPointer
import IS.WheatherApp.feature_wheather.presentation.google_map.component.YourAddress
import IS.WheatherApp.feature_wheather.presentation.ui.theme.NxtDays
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NearMe
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
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GoogleMapScreen(
    navController: NavController,
    viewModel: GoogleMapViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is GoogleMapViewModel.UiEvent.SaveLocation -> {
                    navController.navigate(Screen.ResultAddNewLocation.route + "?data=${viewModel.data.value}")
                }
            }
        }
    }

    val pointerLocation = viewModel.pointerLocation.value
    val pointerAnimationState = viewModel.pointerAnimationState.value
    val mapView = rememberMapViewWithLifecycle()
    val zoomLevel = viewModel.zoomLevel.value
    var address = viewModel.address.value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(GoogleMapEvent.UpdateLocationUI)
                },
                backgroundColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Outlined.NearMe,
                    tint = Color.Black,
                    contentDescription = "NearMe"
                )
            }
        }
    ) {
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
                    map.setOnCameraMoveListener {
                        viewModel.onEvent(GoogleMapEvent.PointerAnimateStateChange(MapPointerMovingState.IDLE))
                        Log.e("mapMove", pointerAnimationState.toString())
                    }
                    map.setOnCameraIdleListener {
                        viewModel.onEvent(GoogleMapEvent.PointerAnimateStateChange(MapPointerMovingState.DRAGGING))
                        viewModel.onEvent(GoogleMapEvent.ZoomLevel(map.cameraPosition.zoom))
                        viewModel.onEvent(GoogleMapEvent.PointerState(map.cameraPosition.target))
                        Log.e("mapStop", pointerAnimationState.toString())
                    }
                }
            }
            YourAddress(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 35.dp),
                address = address
            )
            AnimatedMapPointer(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 52.dp),
                pointerAnimationState = pointerAnimationState
            )
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        start = 16.dp,
                        end = 80.dp,
                        bottom = 18.dp
                    )
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    viewModel.onEvent(GoogleMapEvent.SaveLocation)
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
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }

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
