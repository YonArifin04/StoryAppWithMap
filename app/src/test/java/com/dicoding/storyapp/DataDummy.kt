package com.dicoding.storyapp

import com.dicoding.storyapp.data.ListStoryItems

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItems> {
        val items: MutableList<ListStoryItems> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStoryItems(
                id = i.toString(),
                name = "name $i",
                description = "description $i",
                photoUrl = "photoUrl $i",
                createdAt = "createdAt $i"
            )
            items.add(quote)
        }
        return items
    }
}