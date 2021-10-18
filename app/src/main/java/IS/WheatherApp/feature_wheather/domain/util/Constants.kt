package IS.WheatherApp.feature_wheather.domain.util

import IS.WheatherApp.feature_wheather.presentation.MainActivity

lateinit var APP_ACTIVITY: MainActivity

data class StandardLocation(
    val name: String,
    val lat: Double,
    val lon: Double
)

val SET_OF_LOCATIONS = listOf(
    StandardLocation(name = "Minsk", 53.902284, 27.561831),
    StandardLocation(name = "Moscow", 55.7491973, 37.6097756),
    StandardLocation(name = "Kiev", 50.450441, 30.523550),
    StandardLocation(name = "Warsaw", 52.232282, 21.006672),
    StandardLocation(name = "Berlin", 52.518473, 13.374374),
    StandardLocation(name = "Paris", 48.856551, 2.350974),
    StandardLocation(name = "Rome", 41.902653, 12.495441),
    StandardLocation(name = "London", 51.507128, -0.127548),
    StandardLocation(name = "Madrid", 40.416742, -3.704394),
    StandardLocation(name = "Prague", 50.080187, 14.428407),
    StandardLocation(name = "Dubai", 25.195144, 55.278410),
    StandardLocation(name = "New Delhi", 28.613744, 77.202254),
    StandardLocation(name = "Beijing", 39.902011, 116.391167),
    StandardLocation(name = "Seoul", 51.507128, -0.127548),
    StandardLocation(name = "Tokyo", 35.681426, 139.752762),
    StandardLocation(name = "Canberra", -35.307427, 149.124386),
    StandardLocation(name = "Washington", 38.899827, -77.037454),
    StandardLocation(name = "New York", 40.714771, -74.002817),
    StandardLocation(name = "Los Angeles", 34.055724, -118.246540),
    StandardLocation(name = "Vancouver", 49.284469, -123.117065),
    StandardLocation(name = "Ottawa", 45.400837, -75.701201),
    StandardLocation(name = "Mexico City", 19.432448, -99.133694),
    StandardLocation(name = "Brasilia", -15.802395, -47.889149),
)
