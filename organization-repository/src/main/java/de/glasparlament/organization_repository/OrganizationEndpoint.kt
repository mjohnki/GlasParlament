package de.glasparlament.organization_repository

import de.glasparlament.data.OrganizationList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface OrganizationEndpoint {

    @GET
    suspend fun getOrganizationList(@Url url:String): Response<OrganizationList>
}