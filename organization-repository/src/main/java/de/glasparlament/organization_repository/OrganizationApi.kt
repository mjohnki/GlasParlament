package de.glasparlament.organization_repository

import de.glasparlament.data.BaseApi
import de.glasparlament.data.OrganizationList
import de.glasparlament.data.Transfer

class OrganizationApi(private val endpoint: OrganizationEndpoint) : BaseApi(){

    suspend fun getOrganizationList(url: String): Transfer<OrganizationList> {
        return safeApiCall(
                call = { endpoint.getOrganizationList(url) },
                errorMessage = errorMessageOrganizationList
        )
    }

    companion object{
        const val errorMessageOrganizationList = "Error Fetching OrganizationList"
    }
}