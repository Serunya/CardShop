package com.tailspin.data.di

import com.tailspin.data.repository.ItemRepositoryImpl
import com.tailspin.data.source.local.dao.ItemDao
import com.tailspin.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DataModule  {

    @Singleton
    @Provides
    fun provideAlbumRepository(
        itemDao: ItemDao
    ): ItemRepository {
        return ItemRepositoryImpl(itemDao)
    }

}