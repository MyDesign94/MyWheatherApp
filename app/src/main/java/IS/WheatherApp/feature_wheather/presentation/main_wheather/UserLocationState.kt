package IS.WheatherApp.feature_wheather.presentation.main_wheather

data class UserLocationState(
    val location: Location? = null
)

data class Location(
    val lat: String = "",
    val lon: String = ""
)
