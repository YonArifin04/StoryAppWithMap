package com.dicoding.storyapp.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.storyapp.config.ApiService

class StoryPagingSource(private val apiService: ApiService) : PagingSource<Int, ListStoryItems>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItems> {
        val page = params.key ?: INITIAL_PAGE_INDEX
        return try {
            val response = apiService.getStories(page, params.loadSize)
            LoadResult.Page(
                data = response.listStory,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (response.listStory.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, ListStoryItems>): Int? {
        Log.d("tes4", "getRefreshKey: called")
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}