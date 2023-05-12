package com.gunawan.rapidapi.repositories

import com.gunawan.rapidapi.repositories.model.AnimeByIdResp
import com.gunawan.rapidapi.repositories.model.AnimeResp
import com.gunawan.rapidapi.repositories.model.GenreResp
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RapidAPIRepository @Inject constructor(private val api: APIService) {

    fun genre(): Observable<List<GenreResp>> {
        return api.genre()
    }

    fun anime(page: Int, size: Int, genre: String? = null, search: String? = null): Observable<AnimeResp> {
        return api.anime(page, size, genre, search)
    }

    fun animeById(id: String): Observable<AnimeByIdResp> {
        return api.animeById(id)
    }

}