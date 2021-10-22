package IS.WheatherApp.feature_wheather.presentation

import IS.WheatherApp.feature_wheather.domain.util.APP_ACTIVITY
import IS.WheatherApp.feature_wheather.domain.util.Screen
import IS.WheatherApp.feature_wheather.presentation.add_city.AddNewLocationScreen
import IS.WheatherApp.feature_wheather.presentation.add_city.component.GoogleMapScreen
import IS.WheatherApp.feature_wheather.presentation.main_wheather.MainWeatherScreen
import IS.WheatherApp.feature_wheather.presentation.manager_cities.ManagerCitiesScreen
import IS.WheatherApp.feature_wheather.presentation.nex_seven_days.NextSevenDaysScreen
import IS.WheatherApp.feature_wheather.presentation.result_add_new_location.ResultAddNewLocation
import IS.WheatherApp.feature_wheather.presentation.ui.theme.MyWheatherAppTheme
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.location.* // ktlint-disable no-wildcard-imports
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupContentWindow()
        APP_ACTIVITY = this
        checkPermissions()
        setContent {
            MyWheatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ManagerCitiesScreen.route
                    ) {
                        composable(route = Screen.ManagerCitiesScreen.route) {
                            ManagerCitiesScreen(navController = navController)
                        }
                        composable(
                            route = Screen.MainWeatherScreen.route +
                                "?cityId={cityId}",
                            arguments = listOf(
                                navArgument(
                                    name = "cityId"
                                ) {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            MainWeatherScreen(navController = navController)
                        }
                        composable(route = Screen.AddCityScreen.route) {
                            AddNewLocationScreen(navController = navController)
                        }
                        composable(
                            route = Screen.WeatherNextSevenDays.route +
                                "?cityId={cityId}",
                            arguments = listOf(
                                navArgument(
                                    name = "cityId"
                                ) {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            NextSevenDaysScreen(navController = navController)
                        }
                        composable(
                            route = Screen.ResultAddNewLocation.route +
                                "?data={data}",
                            arguments = listOf(
                                navArgument(name = "data") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            ResultAddNewLocation(navController = navController)
                        }
                        composable(
                            route = Screen.GoogleMap.route
                        ) {
                            GoogleMapScreen(navController)
                        }
                    }
                }
            }
        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !==
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            }
        }
    }

    private fun setupContentWindow() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = 0x00000000
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((
                        ContextCompat.checkSelfPermission(
                                this@MainActivity,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) ===
                            PackageManager.PERMISSION_GRANTED
                        )
                    ) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
