package de.glasparlament.organization_repository

import de.glasparlament.data.OrganizationList
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface OrganizationEndpoint {

    @GET
    fun getOrganizationList(@Url url:String): Deferred<Response<OrganizationList>>
}