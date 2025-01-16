package com.example.calenderapp.di

import android.content.Context
import androidx.room.Room
import com.example.calenderapp.data.local.EventDB
import com.example.calenderapp.data.local.EventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class HiltModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): EventDB {
        return Room.databaseBuilder(
            context,
            EventDB::class.java,
            "events_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideEventDao(database: EventDB): EventDao {
        return database.eventDao()
    }

}