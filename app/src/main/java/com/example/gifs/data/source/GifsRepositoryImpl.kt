package com.example.gifs.data.source

import com.example.gifs.data.source.remote.GifsService

class GifsRepositoryImpl(private val service: GifsService) : GifsRepository {
    override suspend fun searchGif(searchQuery: String, limit: Int, offset: Int) =
        service.getSearchResult(searchQuery, limit, offset).data
}
