package com.example.gifs.domain

import com.example.gifs.data.Gif
import com.example.gifs.data.source.GifsRepository

class GetGifsImpl(private val gifsRepository: GifsRepository) : GetGifs {
    override suspend operator fun invoke(searchQuery: String, limit: Int, offset: Int) =
        gifsRepository.searchGif(searchQuery, limit, offset)
}

interface GetGifs {
    suspend operator fun invoke(searchQuery: String, limit: Int, offset: Int): List<Gif>?
}
