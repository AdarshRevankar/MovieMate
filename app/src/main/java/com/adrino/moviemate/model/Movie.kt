package com.adrino.moviemate.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id")
    val movieId: Int? = 0,
    @SerializedName("backdrop_path")
    val imageUrl: String? = null,
    val first_air_date: String? = null,
    val genre_ids: List<String>? = null,
    @SerializedName("name")
    val movieName: String? = null,
    val origin_country: List<String>? = null,
    val original_language: String? = null,
    val original_name: String? = null,
    val overview: String? = null,
    val popularity: Float? = null,
    val poster_path: String? = null,
    val vote_average: Float? = null,
    val vote_count: Int? = 0
) : Parcelable {

    fun getPosterURL(): String? {
        return poster_path?.let { "https://image.tmdb.org/t/p/w185$it" }
    }

    fun getBackdropURL(): String? {
        return imageUrl?.let { "https://image.tmdb.org/t/p/w300$it" }
    }
}