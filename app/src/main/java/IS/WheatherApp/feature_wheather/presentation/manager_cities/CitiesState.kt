package IS.WheatherApp.feature_wheather.presentation.manager_cities

import IS.WheatherApp.feature_wheather.domain.model.City

data class CitiesState(
    val cities: List<City> = emptyList(),
)
