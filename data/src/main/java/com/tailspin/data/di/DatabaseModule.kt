package com.tailspin.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tailspin.data.source.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, AppDatabase.DB_NAME
        ).allowMainThreadQueries().addMigrations(MIGRATION_1_2).build()
    }

    // Вынести в отельный файл
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("CREATE TABLE IF NOT EXISTS `tag` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL)")
            db.execSQL(
                """
            CREATE TABLE IF NOT EXISTS `ItemTag` (
                `itemId` INTEGER NOT NULL, 
                `tagId` INTEGER NOT NULL, 
                PRIMARY KEY(`itemId`, `tagId`), 
                FOREIGN KEY(`itemId`) REFERENCES `item`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE, 
                FOREIGN KEY(`tagId`) REFERENCES `tag`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
            )""".trimIndent()
            )

            val cursor = db.query("SELECT id, tags From item")
            while (cursor.moveToNext()) {
                val itemId = cursor.getInt(0)
                val tags = cursor.getString(1)
                val tagArray = tags.split(", ")
                tagArray.forEach { tag ->
                    val trimmedTag = tag.trim()
                    db.execSQL(
                        "INSERT OR IGNORE INTO `tag` (`name`) VALUES (?)", arrayOf(trimmedTag)
                    )
                    val tagCursor =
                        db.query("SELECT id FROM `tag` WHERE `name` = ?", arrayOf(trimmedTag))
                    if (tagCursor.moveToFirst()) {
                        val tagId = tagCursor.getInt(0)
                        db.execSQL(
                            "INSERT INTO `item_tag` (`itemId`, `tagId`) VALUES (?, ?)",
                            arrayOf(itemId, tagId)
                        )
                    }
                    tagCursor.close()
                }
            }
            cursor.close()
            db.execSQL("CREATE TABLE IF NOT EXISTS `item_new` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `time` INTEGER NOT NULL, `amount` INTEGER NOT NULL)")
            db.execSQL("INSERT INTO `item_new` (`id`, `name`, `time`, `amount`) SELECT `id`, `name`, `time`, `amount` FROM `item`")
            db.execSQL("DROP TABLE `item`")
            db.execSQL("ALTER TABLE `item_new` RENAME TO `item`")
        }

    }
}