package com.ingridsantos.technicaltestsistecredito.injection.persistence

import android.app.Application
import androidx.room.Room
import com.ingridsantos.technicaltestsistecredito.data.local.TechnicalTestRoomDatabase
import com.ingridsantos.technicaltestsistecredito.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Singleton
    @Provides
    fun provideDatabaseInstance(application: Application): TechnicalTestRoomDatabase {
        return Room.databaseBuilder(
            application,
            TechnicalTestRoomDatabase::class.java,
            TECHNICAL_TEST_DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun userDaoProvider(technicalTestRoomDatabase: TechnicalTestRoomDatabase): UserDao {
        return technicalTestRoomDatabase.userDao()
    }

    private const val TECHNICAL_TEST_DB_NAME = "technical_test_database"
}

