package IS.WheatherApp.di

import IS.WheatherApp.feature_wheather.data.data_source.CitiesDatabase
import IS.WheatherApp.feature_wheather.data.repository.db_repository.CitiesRepositoryImpl
import IS.WheatherApp.feature_wheather.domain.repository.db_repository.CitiesRepository
import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCitiesDatabase(app: Application): CitiesDatabase {
        return Room.databaseBuilder(
            app,
            CitiesDatabase::class.java,
            CitiesDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCitiesRepository(db: CitiesDatabase): CitiesRepository {
        return CitiesRepositoryImpl(db.cityDao)
    }
}
