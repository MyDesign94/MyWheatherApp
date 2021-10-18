package IS.WheatherApp.feature_wheather.domain.use_case.db_use_case

import IS.WheatherApp.feature_wheather.domain.model.City
import IS.WheatherApp.feature_wheather.domain.repository.db_repository.CitiesRepository

class UpdateCityUseCase(
    private val repository: CitiesRepository
) {
    suspend operator fun invoke(city: City) {
        repository.updateCity(city)
    }
}
