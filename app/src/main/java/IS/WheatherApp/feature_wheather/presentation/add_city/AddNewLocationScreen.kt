package IS.WheatherApp.feature_wheather.presentation.add_city

import IS.WheatherApp.feature_wheather.domain.util.SET_OF_LOCATIONS
import IS.WheatherApp.feature_wheather.domain.util.Screen
import IS.WheatherApp.feature_wheather.presentation.add_city.component.LocationItem
import IS.WheatherApp.feature_wheather.presentation.add_city.component.MyLocation
import IS.WheatherApp.feature_wheather.presentation.add_city.old_ver.AddCityViewModel
import IS.WheatherApp.feature_wheather.presentation.ui.theme.BackgroundCardColor
import IS.WheatherApp.feature_wheather.presentation.ui.theme.NxtDays
import IS.WheatherApp.feature_wheather.presentation.ui.theme.TextColor2
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun AddNewLocationScreen(
    navController: NavController,
    viewModel: AddCityViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val (value, onValueChange) = remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddCityViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddCityViewModel.UiEvent.SaveCity -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    maxLines = 1,
                    textStyle = TextStyle(fontSize = 17.sp),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            null,
                            tint = Color.Gray
                        )
                    },
                    trailingIcon = {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "clean search",
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(BackgroundCardColor)
                                .clickable { navController.navigate(Screen.GoogleMap.route) }
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .background(BackgroundCardColor, RoundedCornerShape(16.dp)),
                    placeholder = { Text(text = "Enter location") },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.DarkGray
                    )
                )
                Text(
                    text = "Cancel",
                    style = TextStyle(fontSize = 17.sp),
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    color = NxtDays,
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable { navController.navigateUp() }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Popular cities",
                style = MaterialTheme.typography.subtitle2,
                color = TextColor2
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyVerticalGrid(
                cells = GridCells.Adaptive(100.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                item {
                    MyLocation(navController = navController)
                }
                items(SET_OF_LOCATIONS) { data ->
                    LocationItem(data = data, navController = navController)
                }
            }
        }
    }
}
