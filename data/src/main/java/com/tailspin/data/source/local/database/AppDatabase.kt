package com.tailspin.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tailspin.data.source.local.dao.ItemDao
import com.tailspin.data.source.local.dao.ItemTagDao
import com.tailspin.data.source.local.dao.TagDao
import com.tailspin.data.source.local.entity.ItemEntity
import com.tailspin.data.source.local.entity.ItemTagEntity
import com.tailspin.data.source.local.entity.TagEntity

@Database(
    entities = [ItemEntity::class, ItemTagEntity::class, TagEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val itemDao : ItemDao

    companion object {
        const val DB_NAME = "data.db"
    }


}