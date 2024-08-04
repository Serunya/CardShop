package com.tailspin.data.source.local.database

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


internal val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        Log.d("Migration", "Applying migration from version 0 to 1")
        db.execSQL("CREATE TABLE IF NOT EXISTS `tag` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL)")
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS `item_tag` (
                `itemId` INTEGER NOT NULL, 
                `tagId` INTEGER NOT NULL, 
                PRIMARY KEY(`itemId`, `tagId`), 
                FOREIGN KEY(`itemId`) REFERENCES `item`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE, 
                FOREIGN KEY(`tagId`) REFERENCES `tag`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
            )
        """.trimIndent())
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_item_tag_itemId` ON `item_tag` (`itemId`)")
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_item_tag_tagId` ON `item_tag` (`tagId`)")

        val cursor = db.query("SELECT id, tags FROM item")
        while (cursor.moveToNext()) {
            val itemId = cursor.getInt(0)
            val tags = cursor.getString(1)
            val tagArray = tags.split(", ")
            tagArray.forEach { tag ->
                val trimmedTag = tag.trim()
                db.execSQL("INSERT OR IGNORE INTO `tag` (`name`) VALUES (?)", arrayOf(trimmedTag))
                val tagCursor = db.query("SELECT id FROM `tag` WHERE `name` = ?", arrayOf(trimmedTag))
                if (tagCursor.moveToFirst()) {
                    val tagId = tagCursor.getInt(0)
                    db.execSQL("INSERT INTO `item_tag` (`itemId`, `tagId`) VALUES (?, ?)", arrayOf(itemId, tagId))
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
