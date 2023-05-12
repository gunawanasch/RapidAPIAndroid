package com.gunawan.rapidapi.repositories

import com.gunawan.rapidapi.repositories.model.AnimeByIdResp
import com.gunawan.rapidapi.repositories.model.GenreResp
import com.gunawan.rapidapi.repositories.model.AnimeResp
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("genre")
    fun genre(): Observable<List<GenreResp>>

    @GET("anime")
    fun anime(@Query("page") page: Int,
                    @Query("size") size: Int,
                    @Query("genres") genres: String? = null,
                    @Query("search") search: String? = null): Observable<AnimeResp>

    @GET("anime/by-id/{id}")
    fun animeById(@Path("id") id: String): Observable<AnimeByIdResp>
}