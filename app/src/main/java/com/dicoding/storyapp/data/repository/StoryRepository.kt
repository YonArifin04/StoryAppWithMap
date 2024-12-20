import androidx.lifecycle.asFlow
import androidx.paging.*
import com.dicoding.storyapp.data.*
import com.dicoding.storyapp.config.ApiService
import com.dicoding.storyapp.database.StoryDatabase
import com.dicoding.storyapp.response.AddNewStoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository(private val apiService: ApiService, private val database: StoryDatabase) {

    fun getStories(): Flow<PagingData<ListStoryItems>> {
        return Pager(
            config = PagingConfig(
                pageSize = 15
            ),
            pagingSourceFactory = { StoryPagingSource(apiService) }
        ).flow
    }

    suspend fun uploadNewStory(
        photo: MultipartBody.Part,
        description: RequestBody,
        lat: Float,
        lon: Float
    ): AddNewStoryResponse {
        return apiService.uploadNewStory(description, photo, lat, lon)
    }
}