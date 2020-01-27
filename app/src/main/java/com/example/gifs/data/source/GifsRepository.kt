package com.example.gifs.data.source

import com.example.gifs.data.Gif

interface GifsRepository {
    suspend fun searchGif(searchQuery: String, limit: Int, offset: Int): List<Gif>?
}
