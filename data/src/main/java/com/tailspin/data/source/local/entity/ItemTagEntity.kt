package com.tailspin.data.source.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
    tableName = "item_tag",
    primaryKeys = ["cardId", "tagId"],
    foreignKeys = [
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["itemId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class ItemTagEntity(
    var itemId: Int,
    var tagId: Int
)