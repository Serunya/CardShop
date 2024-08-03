package com.tailspin.data.repository

import com.tailspin.data.source.local.dao.ItemDao
import com.tailspin.domain.model.Item
import com.tailspin.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(private val itemDao: ItemDao) : ItemRepository {
    override fun getItems(): Flow<List<Item>> = flow {
        emit(itemDao.getItemsWithTags().map { it.toItem() })
    }
}