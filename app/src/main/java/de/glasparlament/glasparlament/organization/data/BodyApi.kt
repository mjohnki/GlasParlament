package de.glasparlament.glasparlament.organization.data

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.logging.Logger

class BodyApi(private val endpoint: BodyEndpoint) {

    fun getBodyList(): Observable<Body> {

        return endpoint.getBodyList()
                .map(this::handleResponse)
                .toObservable()
                .flatMapIterable { t: List<Body> -> t }
    }

    private fun handleResponse(response: Response<BodyList>): List<Body> {
        if (response.isSuccessful) {
            return response.body()!!.data
        } else {
            return emptyList()
        }
    }
}