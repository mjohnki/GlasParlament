package de.glasparlament.glasparlament.organization.data

import io.reactivex.Observable
import retrofit2.Response
import java.util.logging.Logger

class OrganizationApi(private val endpoint: OrganizationEndpoint) {

    fun getOrganizationList(url: String): Observable<Organization> {

        return endpoint.getOrganizationList(url)
                .map(this::handleResponse)
                .toObservable()
                .flatMapIterable { t: List<Organization> -> t }
    }

    private fun handleResponse(response: Response<OrganizationList>): List<Organization> {
        if (response.isSuccessful) {
            return response.body()!!.data
        } else {
            return emptyList()
        }
    }
}