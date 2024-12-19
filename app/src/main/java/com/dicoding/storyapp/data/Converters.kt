package com.dicoding.storyapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromListStoryItemsList(value: List<ListStoryItems>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<ListStoryItems>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toListStoryItemsList(value: String?): List<ListStoryItems>? {
        val gson = Gson()
        val type = object : TypeToken<List<ListStoryItems>>() {}.type
        return gson.fromJson(value, type)
    }
}