package de.glasparlament.glasparlament.organization.data

import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface BodyEndpoint {

    @GET("oparl/body/")
    fun getBodyList(): Single<Response<BodyList>>
}
