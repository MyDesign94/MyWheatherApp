package IS.WheatherApp.feature_wheather.domain.repository.db_repository

import IS.WheatherApp.feature_wheather.domain.model.City
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {

    fun getCities(): Flow<List<City>>

    suspend fun getCityById(id: Int): City?

    suspend fun insertCity(city: City)

    suspend fun deleteCity(city: City)

    suspend fun updateCity(city: City)
}