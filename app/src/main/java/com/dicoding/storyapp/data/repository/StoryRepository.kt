import androidx.lifecycle.asFlow
import androidx.paging.*
import com.dicoding.storyapp.data.*
import com.dicoding.storyapp.config.ApiService
import com.dicoding.storyapp.database.StoryDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoryRepository(private val apiService: ApiService, private val database: StoryDatabase) {

    fun getStories(): Flow<PagingData<ListStoryItems>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { StoryPagingSource(apiService) }
        ).flow
    }
}