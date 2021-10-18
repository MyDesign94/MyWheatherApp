package IS.WheatherApp.di

import IS.WheatherApp.feature_wheather.domain.repository.db_repository.CitiesRepository
import IS.WheatherApp.feature_wheather.domain.repository.weather_repository.WeatherRepository
import IS.WheatherApp.feature_wheather.domain.use_case.db_use_case.* // ktlint-disable no-wildcard-imports
import IS.WheatherApp.feature_wheather.domain.use_case.weather_use_case.GetWeatherUseCase
import IS.WheatherApp.feature_wheather.domain.use_case.weather_use_case.WeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideWeatherUseCase(repository: WeatherRepository): WeatherUseCase {
        return WeatherUseCase(
            getWeather = GetWeatherUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCitiesUseCase(repository: CitiesRepository): CitiesUseCase {
        return CitiesUseCase(
            getCities = GetCitiesUseCase(repository),
            getCity = GetCityUseCase(repository),
            addCity = AddCityUseCase(repository),
            deleteCity = DeleteCityUseCase(repository),
            updateCity = UpdateCityUseCase(repository)
        )
    }
}
