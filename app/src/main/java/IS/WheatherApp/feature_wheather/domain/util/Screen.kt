package IS.WheatherApp.feature_wheather.domain.util

sealed class Screen(val route: String) {
    object MainWeatherScreen : Screen("main_weather_screen")
    object ManagerCitiesScreen : Screen("manager_cities_screen")
    object AddCityScreen : Screen("add_city_screen")
    object WeatherNextSevenDays : Screen("weather_next_seven_days")
    object ResultAddNewLocation : Screen("result_add_new_location")
}
