package com.example.gifs.domain

import com.example.gifs.data.Gif
import com.example.gifs.data.source.GifsRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetGifsImplTest {
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var getGifs: GetGifsImpl
    private lateinit var repository: GifsRepository
    private val searchQuery = "Cat"
    private val limit = 20
    private val offset = 0
    private val successList = listOf(
        Gif(id = "123", images = mapOf(), title = "testTitle", username = "testUsername")
    )

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = Mockito.mock(GifsRepository::class.java)
        getGifs = GetGifsImpl(repository)
    }

    @Test
    fun `get GIFs from repository`() = runBlocking {
        whenever(repository.searchGif(searchQuery, limit, offset)).thenReturn(successList)
        val result: List<Gif>? = getGifs.invoke(searchQuery, limit, offset)
        assertThat(result?.get(0)?.id, `is`(successList[0].id))
    }
}
