package com.example.a4kfullwallpapers.db.databasemodule

import android.app.Application
import com.example.a4kfullwallpapers.ImageSearchApplication
import dagger.Provides
import javax.inject.Singleton
import androidx.room.Room
import com.example.a4kfullwallpapers.db.dao.PhotoDao
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    companion object {

        @Provides
        @Singleton
        fun providePhotoDB(application: Application): PhotoDB {
            return Room.databaseBuilder(application, PhotoDB::class.java, "App Database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }

        @Provides
        @Singleton
        fun providePokeDao(photoDB: PhotoDB): PhotoDao{
            return photoDB.photoDao()
        }
    }
}