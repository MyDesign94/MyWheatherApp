package IS.WheatherApp.feature_wheather.data.data_source

import IS.WheatherApp.feature_wheather.domain.model.City
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [City::class],
    version = 1
)
abstract class CitiesDatabase : RoomDatabase(){

    abstract val cityDao : CityDao

    companion object {
        const val DATABASE_NAME = "cities_db"
    }
}