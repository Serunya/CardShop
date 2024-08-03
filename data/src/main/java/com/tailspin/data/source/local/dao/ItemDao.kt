package com.tailspin.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.tailspin.data.source.local.entity.ItemEntity
import com.tailspin.data.source.local.model.ItemWithTags

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemEntity)

    @Query("SELECT * FROM item")
    suspend fun getAllItems(): List<ItemEntity>

    @Transaction
    @Query("SELECT * FROM item")
    suspend fun getItemsWithTags(): List<ItemWithTags>
}