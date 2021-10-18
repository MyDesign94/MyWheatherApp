package IS.WheatherApp.feature_wheather.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    @PrimaryKey val id: Int? = null,
    val lat: String,
    val lon: String,
    val name: String = "",
    val icon: String = "",
    val tempNow: String = "",
    val weatherData: String = ""
)

class InvalidCityItemException(message: String): Exception(message)
