package com.tailspin.data.repository

import com.tailspin.data.source.local.dao.ItemDao
import com.tailspin.domain.model.Item
import com.tailspin.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(private val itemDao: ItemDao) : ItemRepository {
    override fun getItems(): Flow<List<Item>> = itemDao.getItemsWithTags()
        .map { itemEntities ->
            itemEntities.map { it.toItem() }
        }

    override fun searchItems(query: String): Flow<List<Item>> = itemDao.searchItemsByName("%$query%")
        .map { itemEntities -> itemEntities.map { it.toItem() } }

    override suspend fun editAmountItemById(itemId: Int, newAmount: Int) {
        itemDao.updateAmountById(itemId,newAmount)
    }


    override suspend fun deleteItemById(itemId: Int) {
        itemDao.deleteItemById(itemId)
    }

}