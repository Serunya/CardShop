package com.tailspin.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.tailspin.data.source.local.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemEntity)

    @Transaction
    @Query("SELECT * FROM item")
    fun getItemsWithTags(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM item WHERE name LIKE :query")
    fun searchItemsByName(query: String): Flow<List<ItemEntity>>

    @Query("DELETE FROM item Where id = :itemId")
    suspend fun deleteItemById(itemId: Int)

    @Query("UPDATE item SET amount = :newAmount WHERE id = :itemId")
    suspend fun updateAmountById(itemId : Int, newAmount : Int)
}