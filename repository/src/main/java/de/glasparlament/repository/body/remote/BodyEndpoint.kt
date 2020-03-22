package de.glasparlament.repository.body.remote

import retrofit2.http.GET

interface BodyEndpoint {

    @GET("oparl/body/")
    suspend fun getBodyList(): BodyList
}
