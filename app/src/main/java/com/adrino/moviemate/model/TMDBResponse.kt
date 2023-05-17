package com.adrino.moviemate.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * TheMovieDB class Response
 */
@Parcelize
data class TMDBResponse(
    val page: Int? = null,
    val total_pages: Int? = null,
    val total_results: Int? = null,
    val results: ArrayList<Movie>? = null
) : Parcelable