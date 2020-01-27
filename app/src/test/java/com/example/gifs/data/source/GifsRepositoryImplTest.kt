package com.example.gifs.data.source

import com.example.gifs.data.DataResponse
import com.example.gifs.data.Gif
import com.example.gifs.data.source.remote.GifsService
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GifsRepositoryImplTest {
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var gifsRepository: GifsRepositoryImpl
    private lateinit var gifsService: GifsService
    private val searchQuery = "Cat"
    private val limit = 20
    private val offset = 0
    private val successList = listOf(
        Gif(id = "123", images = mapOf(), title = "testTitle", username = "testUsername")
    )
    private val dataResponse = DataResponse(data = successList)

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        gifsService = Mockito.mock(GifsService::class.java)
        gifsRepository = GifsRepositoryImpl(gifsService)
    }

    @Test
    fun `get GIFs list from service`() = runBlocking {
        whenever(gifsService.getSearchResult(searchQuery, limit, offset)).thenReturn(dataResponse)
        val result: List<Gif>? = gifsRepository.searchGif(searchQuery, limit, offset)
        assertThat(result?.get(0)?.id, CoreMatchers.`is`(successList[0].id))
    }
}
