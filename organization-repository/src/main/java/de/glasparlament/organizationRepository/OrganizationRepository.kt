package de.glasparlament.organizationRepository

import de.glasparlament.data.OrganizationList
import de.glasparlament.data.Transfer

interface OrganizationRepository {
    suspend fun getOrganizationList(url: String): Transfer<OrganizationList>
}

class OrganizationRepositoryImpl(
        private val organizationApi: OrganizationApi) : OrganizationRepository {

    override suspend fun getOrganizationList(url: String): Transfer<OrganizationList> {
        return organizationApi.getOrganizationList(url)
    }
}

