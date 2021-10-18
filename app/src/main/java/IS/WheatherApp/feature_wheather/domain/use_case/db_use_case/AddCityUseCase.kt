package IS.WheatherApp.feature_wheather.domain.use_case.db_use_case

import IS.WheatherApp.feature_wheather.domain.model.City
import IS.WheatherApp.feature_wheather.domain.model.InvalidCityItemException
import IS.WheatherApp.feature_wheather.domain.repository.db_repository.CitiesRepository


class AddCityUseCase (
    private val repository: CitiesRepository
) {

    @Throws(InvalidCityItemException::class)
    suspend operator fun invoke(city: City) {
        if (city.lat.isBlank() && city.lon.isBlank()) {
            throw InvalidCityItemException("Location can't be empty")
        }
        repository.insertCity(city)
    }
}