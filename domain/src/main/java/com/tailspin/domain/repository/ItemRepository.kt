package com.tailspin.domain.repository

import com.tailspin.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getItems() : Flow<List<Item>>
}