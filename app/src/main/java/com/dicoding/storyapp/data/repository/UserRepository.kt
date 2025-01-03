package com.dicoding.storyapp.data.repository

import com.dicoding.storyapp.config.ApiConfig
import com.dicoding.storyapp.data.pref.UserModel
import com.dicoding.storyapp.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow
import com.dicoding.storyapp.response.LoginResponse
import com.dicoding.storyapp.config.ApiService
import com.dicoding.storyapp.util.SessionManager

class UserRepository private constructor(
    val userPreference: UserPreference
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun login(email: String, password: String): LoginResponse {
        return ApiConfig.getApiService(userPreference).login(email, password)
    }
    companion object {
        @Volatile
        var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}