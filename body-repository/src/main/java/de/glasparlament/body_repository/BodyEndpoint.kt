package de.glasparlament.body_repository

import de.glasparlament.data.BodyList
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface BodyEndpoint {

    @GET("oparl/body/")
    suspend fun getBodyList(): Response<BodyList>
}
