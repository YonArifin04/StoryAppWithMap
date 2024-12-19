// StoryDatabase.kt
package com.dicoding.storyapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dicoding.storyapp.data.Converters
import com.dicoding.storyapp.data.ListStoryItems
import com.dicoding.storyapp.data.StoryResponseItem

@Database(entities = [StoryResponseItem::class, ListStoryItems::class], version = 1)
@TypeConverters(Converters::class)
abstract class StoryDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase? = null

        fun getInstance(context: Context): StoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoryDatabase::class.java,
                    "story_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}