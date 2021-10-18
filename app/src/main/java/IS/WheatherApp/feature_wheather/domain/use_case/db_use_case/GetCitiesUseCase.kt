package IS.WheatherApp.feature_wheather.domain.use_case.db_use_case

import IS.WheatherApp.feature_wheather.domain.model.City
import IS.WheatherApp.feature_wheather.domain.repository.db_repository.CitiesRepository
import kotlinx.coroutines.flow.Flow

class GetCitiesUseCase(
    private val repository: CitiesRepository
) {

    operator fun invoke(): Flow<List<City>> {
        return repository.getCities()
    }
}
