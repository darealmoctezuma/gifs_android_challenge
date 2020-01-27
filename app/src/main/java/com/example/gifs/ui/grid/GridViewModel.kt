package com.example.gifs.ui.grid

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.gifs.data.Gif
import com.example.gifs.domain.GetGifsImpl
import com.example.gifs.ui.grid.paging.GifsDataSource
import com.example.gifs.ui.grid.paging.LIMIT

sealed class GridViewState {
    class Success(val gifs: PagedList<Gif>) : GridViewState()
}

class GridViewModel(private val getGifs: GetGifsImpl) : ViewModel() {
    private val config = PagedList.Config.Builder()
        .setPageSize(LIMIT)
        .setEnablePlaceholders(false)
        .build()

    private val searchQuery = MutableLiveData<String>()

    private val postsLiveData = searchQuery.switchMap {
        initializedPagedListBuilder(it, config).build()
    }

    private fun initializedPagedListBuilder(query: String, config: PagedList.Config):
            LivePagedListBuilder<Int, Gif> {

        val dataSourceFactory = object : DataSource.Factory<Int, Gif>() {
            override fun create() = GifsDataSource(query, getGifs, viewModelScope)
        }
        return LivePagedListBuilder<Int, Gif>(dataSourceFactory, config)
    }

    fun search(query: String) {
        if (query.isNotEmpty()) {
            searchQuery.postValue(query)
        }
    }

    val gridViewState = postsLiveData.switchMap {
        liveData {
            emit(GridViewState.Success(it))
        }
    }

    fun getSearchQuery() = searchQuery.value
}
