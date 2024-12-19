import androidx.lifecycle.asFlow
import androidx.paging.*
import com.dicoding.storyapp.data.*
import com.dicoding.storyapp.config.ApiService
import com.dicoding.storyapp.database.StoryDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoryRepository(private val apiService: ApiService, private val database: StoryDatabase) {

    fun getStories(): Flow<PagingData<StoryResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { StoryPagingSource(apiService) }
        ).liveData.asFlow().map { pagingData ->
            pagingData.map { listStoryItem ->
                StoryResponseItem(
                    id = 0, // or any default value
                    listStory = listOf(listStoryItem),
                    error = null,
                    message = null
                )
            }
        }
    }
}