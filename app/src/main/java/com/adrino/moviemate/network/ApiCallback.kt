package com.adrino.moviemate.network

import com.adrino.moviemate.model.TMDBResponse

interface ApiCallback {

    fun onSuccess(tag: String, response: TMDBResponse?)

    fun onError(tag: String)

    fun onFailure(tag: String, response: TMDBResponse?)
}