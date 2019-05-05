package de.glasparlament.glasparlament.organization.data

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface OrganizationEndpoint {

    @GET
    fun getOrganizationList(@Url url:String): Single<Response<OrganizationList>>
}