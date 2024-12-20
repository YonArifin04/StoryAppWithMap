package com.dicoding.storyapp.view.main

import StoryRepository
import android.content.ContentValues.TAG
import android.util.Log
import androidx.core.view.ContentInfoCompat.Flags
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.response.AddNewStoryResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class AddStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _uploadResponse = MutableLiveData<AddNewStoryResponse>()
    val uploadResponse: LiveData<AddNewStoryResponse> = _uploadResponse

    private val _isLoading = MutableLiveData<Boolean>()

    fun uploadStory(
        photo: MultipartBody.Part,
        description: RequestBody,
        latPart: Float,
        lonPart: Float
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.uploadNewStory(photo, description, latPart, lonPart)
                }
                _uploadResponse.value = response
            } catch (e: HttpException) {
                _uploadResponse.value = AddNewStoryResponse(
                    error = true,
                    message = "Upload failed: ${e.message()}"
                )
            } catch (e: Exception) {
                _uploadResponse.value = AddNewStoryResponse(
                    error = true,
                    message = "Upload failed: ${e.message}"
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    companion object {
        private const val TAG = "AddStoryViewModel"
    }
}