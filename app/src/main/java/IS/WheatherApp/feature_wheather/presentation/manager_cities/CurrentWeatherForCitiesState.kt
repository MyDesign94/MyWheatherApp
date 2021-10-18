package IS.WheatherApp.feature_wheather.presentation.manager_cities

import IS.WheatherApp.feature_wheather.domain.model.weather_model.CurrentCityWeather

data class CurrentWeatherForCitiesState(
    val allCities: List<CurrentCityWeather> = listOf()
)
