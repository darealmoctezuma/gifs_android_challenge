package com.example.gifs.ui.grid

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.gifs.data.Gif
import com.example.gifs.domain.GetGifsImpl
import com.example.gifs.ui.grid.paging.LIMIT
import com.example.gifs.ui.grid.paging.OFFSET

sealed class GridNoPagingViewState {
    object Loading : GridNoPagingViewState()
    object Error : GridNoPagingViewState()
    class Success(val gifs: List<Gif>) : GridNoPagingViewState()
}

class GridNoPagingViewModel(getGifs: GetGifsImpl) : ViewModel() {
    private val searchQuery = MutableLiveData<String>()

    val gridViewState = searchQuery.switchMap {
        liveData {
            emit(GridNoPagingViewState.Loading)
            runCatching {
                getGifs(it, LIMIT, OFFSET)
            }.onSuccess {
                it?.let {
                    emit(GridNoPagingViewState.Success(it))
                }
            }.onFailure {
                emit(GridNoPagingViewState.Error)
            }
        }
    }

    fun search(query: String) {
        if (query.isNotEmpty()) {
            searchQuery.postValue(query)
        }
    }
}
