package com.adrino.moviemate.components

import com.adrino.moviemate.network.ApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient private constructor() {

    var apiRepository: ApiService

    init {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        apiRepository = retrofit.create(ApiService::class.java)
    }

    fun getMovieMateRepository(): ApiService {
        return apiRepository
    }

    companion object {


        @get:Synchronized
        var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    field = RetrofitClient()
                }
                return field
            }
            private set
    }
}