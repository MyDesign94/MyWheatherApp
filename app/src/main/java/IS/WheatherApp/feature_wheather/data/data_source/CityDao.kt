package IS.WheatherApp.feature_wheather.data.data_source

import IS.WheatherApp.feature_wheather.domain.model.City
import androidx.room.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Query("SELECT * FROM city")
    fun getCities(): Flow<List<City>>

    @Query("SELECT * FROM city WHERE id = :id")
    suspend fun getCityById(id: Int): City?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: City)

    @Delete
    suspend fun deleteCity(city: City)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCity(city: City)
}
