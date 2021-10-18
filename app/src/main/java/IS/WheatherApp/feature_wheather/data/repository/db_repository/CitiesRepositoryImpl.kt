package IS.WheatherApp.feature_wheather.data.repository.db_repository

import IS.WheatherApp.feature_wheather.data.data_source.CityDao
import IS.WheatherApp.feature_wheather.domain.model.City
import IS.WheatherApp.feature_wheather.domain.repository.db_repository.CitiesRepository
import kotlinx.coroutines.flow.Flow

class CitiesRepositoryImpl(
    private val dao: CityDao
) : CitiesRepository {
    override fun getCities(): Flow<List<City>> {
        return dao.getCities()
    }

    override suspend fun getCityById(id: Int): City? {
        return dao.getCityById(id)
    }

    override suspend fun insertCity(city: City) {
        return dao.insertCity(city)
    }

    override suspend fun deleteCity(city: City) {
        return dao.deleteCity(city)
    }

    override suspend fun updateCity(city: City) {
        return dao.updateCity(city)
    }
}
