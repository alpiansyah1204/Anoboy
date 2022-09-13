package com.example.submission1.core.domain.repository

import com.example.submission1.core.domain.model.Anime
import kotlinx.coroutines.flow.Flow

interface IAnimeRepository {
    fun getSearchAnime(query: String): Flow<com.example.submission1.core.data.Resource<List<Anime>>>

    fun getDetailAnime(id: String): Flow<com.example.submission1.core.data.Resource<Anime>>

    fun getFavoriteAnime(): Flow<List<Anime>>

    fun isFavoriteAnime(id: String): Int

    suspend fun insertFavoriteAnime(anime: Anime)

    suspend fun deleteFavoriteAnime(anime: Anime): Int
}