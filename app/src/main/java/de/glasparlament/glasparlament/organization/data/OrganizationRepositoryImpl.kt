package de.glasparlament.glasparlament.organization.data

import de.glasparlament.glasparlament.organization.domain.OrganizationRepository
import io.reactivex.Observable

class OrganizationRepositoryImpl(
        private val bodyApi: BodyApi,
        private val organizationApi: OrganizationApi
) : OrganizationRepository {

    override fun getBodyOrganizationList(): Observable<BodyOrganization> {

        return bodyApi.getBodyList()
                .flatMap { body: Body ->
                    return@flatMap organizationApi.getOrganizationList(body.organization).map { organization: Organization ->
                        BodyOrganizationMapper.map(body, organization)
                    }
                }
    }
}

