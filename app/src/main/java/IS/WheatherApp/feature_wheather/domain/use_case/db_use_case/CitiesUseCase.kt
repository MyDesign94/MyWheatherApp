package IS.WheatherApp.feature_wheather.domain.use_case.db_use_case

data class CitiesUseCase(
    val getCities: GetCitiesUseCase,
    val addCity: AddCityUseCase,
    val getCity: GetCityUseCase,
    val deleteCity: DeleteCityUseCase,
    val updateCity: UpdateCityUseCase
)
