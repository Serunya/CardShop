package com.tailspin.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tailspin.data.source.local.dao.ItemDao
import com.tailspin.data.source.local.entity.ItemEntity

@Database(
    entities = [ItemEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract val itemDao : ItemDao

    companion object {
        const val DB_NAME = "data.db"
    }


}