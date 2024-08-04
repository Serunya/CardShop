package com.tailspin.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tailspin.data.source.local.converters.TagListConverter
import com.tailspin.domain.model.Item
import com.tailspin.domain.model.Tag

@Entity(tableName = "item")
@TypeConverters(TagListConverter::class)
data class ItemEntity(
    @PrimaryKey var id: Int,
    var name: String,
    var time: Long,
    var tags: List<String>,
    var amount: Int
) {
    fun toItem(): Item =
        Item(id, name, time, tags.map { Tag(it) }, amount)
}