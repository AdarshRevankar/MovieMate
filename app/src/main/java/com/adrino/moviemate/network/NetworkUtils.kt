package com.adrino.moviemate.network

import com.adrino.moviemate.model.TMDBResponse
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber

class NetworkUtils {
    companion object {
        fun makeNetworkCall(tag: String, flowable: Flowable<TMDBResponse>?, callback: ApiCallback?): DisposableSubscriber<TMDBResponse>? {
            return flowable?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread(), true)?.subscribeWith(object: DisposableSubscriber<TMDBResponse>() {
                    override fun onNext(response: TMDBResponse) {
                        Logger.d("ON NEXT")
                        Logger.d("ON NEXT")
                        if (response.results == null) {
                            callback?.onFailure(tag, response)
                        } else {
                            callback?.onSuccess(tag, response)
                        }
                    }

                    override fun onError(t: Throwable?) {
                        Logger.d("ON ERROR")
                        Logger.d("Error Message : ${t?.localizedMessage}}")
                        callback?.onError(tag)
                    }

                    override fun onComplete() {
                        Logger.d("ON COMPLETE")
                    }

                })
        }
    }
}