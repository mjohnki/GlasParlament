package de.glasparlament.repository.body

import de.glasparlament.data.BodyList
import retrofit2.Response
import retrofit2.http.GET

interface BodyEndpoint {

    @GET("oparl/body/")
    suspend fun getBodyListAsResponse(): Response<BodyList>

    @GET("oparl/body/")
    suspend fun getBodyList(): BodyList
}
