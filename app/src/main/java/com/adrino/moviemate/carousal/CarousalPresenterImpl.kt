package com.adrino.moviemate.carousal

import android.content.Context
import com.adrino.moviemate.BuildConfig
import com.adrino.moviemate.FETCH_MOVIES
import com.adrino.moviemate.LANGUAGE
import com.adrino.moviemate.components.RetrofitClient
import com.adrino.moviemate.model.TMDBResponse
import com.adrino.moviemate.network.ApiCallback
import com.adrino.moviemate.network.NetworkUtils

class CarousalPresenterImpl(
    private val context: Context?,
    private val view: CarousalContract.View?
) : CarousalContract.Presenter, ApiCallback {

    override fun fetchMovieData() {
        view?.showLoader()
        NetworkUtils.makeNetworkCall(
            FETCH_MOVIES,
            RetrofitClient.instance?.getMovieMateRepository()?.getMovies(
                BuildConfig.API_KEY,
                LANGUAGE,
                1
            ), this
        )
    }

    override fun onSuccess(tag: String, response: TMDBResponse?) {
        view?.hideLoader()
        when (tag) {
            FETCH_MOVIES -> view?.onMovieDataLoaded(response)
        }
    }

    override fun onError(tag: String) {
        view?.hideLoader()
        when (tag) {
            FETCH_MOVIES -> view?.onMovieDataLoaded(null)
        }
    }

    override fun onFailure(tag: String, response: TMDBResponse?) {
        view?.hideLoader()
        when (tag) {
            FETCH_MOVIES -> view?.onMovieDataLoaded(null)
        }
    }

}