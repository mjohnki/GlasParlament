package de.glasparlament.organization

import de.glasparlament.data.Transfer
import de.glasparlament.body_repository.BodyRepository
import de.glasparlament.data.Organization
import de.glasparlament.organization_repository.OrganizationRepository

class OrganizationListUseCase(private val organizationRepository: OrganizationRepository,
                              private val bodyRepository: BodyRepository) {

    suspend fun execute(): Transfer<List<BodyOrganization>> {
        return when (val bodies = bodyRepository.getBodyList()) {
            is Transfer.Error -> bodies
            is Transfer.Success -> {
                val bodyOrganizations = ArrayList<BodyOrganization>()
                bodies.data.data.forEach { body ->
                    run {
                        when (val organizations = organizationRepository.getOrganizationList(body.organization)) {
                            is Transfer.Success -> organizations.data.data.forEach { organization: Organization ->
                                bodyOrganizations.add(BodyOrganizationMapper.map(body, organization))
                            }
                        }


                    }
                }
                Transfer.Success(bodyOrganizations)
            }
        }
    }
}