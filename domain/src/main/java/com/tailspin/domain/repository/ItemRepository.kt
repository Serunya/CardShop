package com.tailspin.domain.repository

import com.tailspin.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getItems() : Flow<List<Item>>
    fun searchItems(query : String) : Flow<List<Item>>
    suspend fun editAmountItemById(itemId: Int, newAmount : Int)
    suspend fun deleteItemById(itemId : Int)
}