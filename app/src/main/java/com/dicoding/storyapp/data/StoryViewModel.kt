package com.dicoding.storyapp.data

import StoryRepository
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.storyapp.data.pref.UserPreference
import com.dicoding.storyapp.injection.StoryInjection
import kotlinx.coroutines.flow.Flow

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    val stories: Flow<PagingData<ListStoryItems>> =
        storyRepository.getStories().cachedIn(viewModelScope)
}

class StoryModelFactory(private val context: Context, private val userPreference: UserPreference) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                StoryViewModel(StoryInjection.provideStoryRepository(context, userPreference)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}