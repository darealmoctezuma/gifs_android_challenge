package com.example.gifs.ui.grid.paging

import androidx.paging.PositionalDataSource
import com.example.gifs.data.Gif
import com.example.gifs.domain.GetGifsImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val LIMIT = 50
const val OFFSET = 0

class GifsDataSource(
    private val query: String,
    val getGifs: GetGifsImpl,
    private val scope: CoroutineScope
) :
    PositionalDataSource<Gif>() {

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<Gif?>
    ) {
        val position = computeInitialLoadPosition(params, LIMIT)

        scope.launch {
            runCatching {
                getGifs(query, LIMIT, OFFSET)
            }.onSuccess {
                it?.let {
                    callback.onResult(it, position, it.size)
                }
            }.onFailure {
                callback.onError(it)
            }
        }
    }

    override fun loadRange(
        params: LoadRangeParams,
        callback: LoadRangeCallback<Gif?>
    ) {

        scope.launch {
            runCatching {
                getGifs(query, params.loadSize, params.startPosition)
            }.onSuccess {
                it?.let {
                    callback.onResult(it)
                }
            }.onFailure {
                callback.onError(it)
            }

        }
    }
}
