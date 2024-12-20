package com.dicoding.storyapp.view.main

import StoryAdapter
import StoryRepository
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.dicoding.storyapp.DataDummy
import com.dicoding.storyapp.MainDispatcherRule
import com.dicoding.storyapp.data.ListStoryItems
import com.dicoding.storyapp.data.StoryViewModel
import com.dicoding.storyapp.response.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    @Test
    fun `when Get Stories Should Not Null and Return Data`() = runTest {
        val dummyStories = DataDummy.generateDummyStoryResponse()
        val data: PagingData<ListStoryItems> = PagingTestDataSources.snapshot(dummyStories)
        val expectedStories: Flow<PagingData<ListStoryItems>> = flowOf(data)
        Mockito.`when`(storyRepository.getStories()).thenReturn(expectedStories)

        val storyViewModel = StoryViewModel(storyRepository)
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = ListUpdateCallbackHelper(),
            workerDispatcher = Dispatchers.Main,
        )

        val job = launch {
            storyViewModel.stories.collectLatest {
                differ.submitData(it)
            }
        }

        advanceUntilIdle()
        job.cancel()

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStories.size, differ.snapshot().size)
        Assert.assertEquals(dummyStories[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Stories Empty Should Return No Data`() = runTest {
        val data: PagingData<ListStoryItems> = PagingData.from(emptyList())
        val expectedStories: Flow<PagingData<ListStoryItems>> = flowOf(data)
        Mockito.`when`(storyRepository.getStories()).thenReturn(expectedStories)

        val storyViewModel = StoryViewModel(storyRepository)
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = ListUpdateCallbackHelper(),
            workerDispatcher = Dispatchers.Main,
        )

        val job = launch {
            storyViewModel.stories.collectLatest {
                differ.submitData(it)
            }
        }

        advanceUntilIdle()
        job.cancel()

        Assert.assertEquals(0, differ.snapshot().size)
    }
}

object PagingTestDataSources {
    fun snapshot(items: List<ListStoryItems>): PagingData<ListStoryItems> {
        return PagingData.from(items)
    }
}

class ListUpdateCallbackHelper : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}