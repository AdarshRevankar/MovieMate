package com.adrino.moviemate.network

import com.adrino.moviemate.model.TMDBResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
    }

    @GET("3/tv/popular")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("language") page: Int?
    ): Flowable<TMDBResponse>
}