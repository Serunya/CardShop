package com.tailspin.data.source.local.converters

import android.util.Log
import androidx.room.TypeConverter

class TagListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val cleanedString = value.removePrefix("[").removeSuffix("]").trim()
        if(cleanedString.isEmpty()) return emptyList()
        return cleanedString.split(", ")
            .map { it.trim().removeSurrounding("\"") }
            .let { it.ifEmpty { emptyList() } }
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        val stringBuilder = StringBuilder("[")

        list.forEachIndexed { index, item ->
            stringBuilder.append("\"${item}\"")
            if (index < list.size - 1) {
                stringBuilder.append(", ")
            }
        }

        stringBuilder.append("]")
        return stringBuilder.toString()
    }
}