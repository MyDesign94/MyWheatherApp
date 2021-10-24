package IS.WheatherApp.di

import IS.WheatherApp.feature_wheather.data.remote.YandexWeatherApi
import IS.WheatherApp.feature_wheather.data.repository.weather_repository.WeatherRepositoryImpl
import IS.WheatherApp.feature_wheather.domain.repository.weather_repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideYandexWeatherApi(): YandexWeatherApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.weather.yandex.ru")
            .build()
            .create(YandexWeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: YandexWeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }
}
