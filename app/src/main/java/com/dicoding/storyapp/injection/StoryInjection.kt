package com.dicoding.storyapp.injection

import StoryRepository
import android.content.Context
import android.util.Log
import com.dicoding.storyapp.config.ApiConfig
import com.dicoding.storyapp.config.ApiService
import com.dicoding.storyapp.data.pref.UserPreference
import com.dicoding.storyapp.data.repository.UserRepository
import com.dicoding.storyapp.database.StoryDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StoryInjection {
    private fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    private fun provideDatabase(context: Context): StoryDatabase {
        return StoryDatabase.getInstance(context)
    }

    fun provideStoryRepository(context: Context, userPreference: UserPreference): StoryRepository {
        Log.d("tes1", "$context, $userPreference")
        val database = StoryDatabase.getInstance(context)
        val apiService = ApiConfig.getApiService(userPreference)
        return StoryRepository(apiService, database)
    }
}
