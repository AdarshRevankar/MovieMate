package com.adrino.moviemate.carousal

import com.adrino.moviemate.model.TMDBResponse

interface CarousalContract {

    interface View {
        fun onMovieDataLoaded(tmdbResponse: TMDBResponse?)

        fun showLoader()

        fun hideLoader()
    }

    interface Presenter {
        fun fetchMovieData()
    }
}