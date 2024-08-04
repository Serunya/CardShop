package com.tailspin.domain.usecase

import com.tailspin.domain.model.Item
import com.tailspin.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    fun getItems() : Flow<List<Item>> = itemRepository.getItems()
    fun searchItem(query : String) : Flow<List<Item>> = itemRepository.searchItems(query)
    suspend fun editAmountItems(item: Item, newAmount : Int) = itemRepository.editAmountItemById(item.id, newAmount)
    suspend fun deleteItem(item: Item) = itemRepository.deleteItemById(item.id)
}