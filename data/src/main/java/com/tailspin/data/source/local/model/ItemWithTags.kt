/*
package com.tailspin.data.source.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.tailspin.data.source.local.entity.ItemEntity
import com.tailspin.data.source.local.entity.ItemTagEntity
import com.tailspin.data.source.local.entity.TagEntity
import com.tailspin.domain.model.Item
import com.tailspin.domain.model.Tag

data class ItemWithTags(
    @Embedded val item: ItemEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            ItemTagEntity::class,
            parentColumn = "itemId",
            entityColumn = "tagId"
        )
    )
    val tags: List<TagEntity>
) {
    fun toItem(): Item {
        return Item(item.id, item.name, item.time, tags.map { Tag(it.name) }, item.amount)
    }
}
*/
