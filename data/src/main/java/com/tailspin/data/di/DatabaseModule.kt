package com.tailspin.data.di

import android.content.Context
import androidx.room.Room
import com.tailspin.data.source.local.dao.ItemDao
import com.tailspin.data.source.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, AppDatabase.DB_NAME
        ).createFromAsset("data.db")
            .build()
    }


    @Provides
    fun provideItemDao(appDatabase: AppDatabase) : ItemDao {
        return appDatabase.itemDao
    }


}
