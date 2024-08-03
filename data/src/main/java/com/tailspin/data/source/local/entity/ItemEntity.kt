package com.tailspin.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "item")
data class ItemEntity (
    @PrimaryKey var id : Long,
    var name : String,
    var time : Int,
    var amount : Int
)