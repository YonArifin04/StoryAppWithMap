package com.dicoding.storyapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "story")
data class StoryResponseItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @field:SerializedName("listStory")
    val listStory: List<ListStoryItems>,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

@Entity(tableName = "list_story")
data class ListStoryItems(
    @PrimaryKey(autoGenerate = true)
    val ids: Int = 0,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

)