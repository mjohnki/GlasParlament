package de.glasparlament.organization

import de.glasparlament.data.Transfer
import de.glasparlament.bodyRepository.BodyRepository
import de.glasparlament.data.Body
import de.glasparlament.data.Organization
import de.glasparlament.organizationRepository.OrganizationRepository

class OrganizationListUseCase(private val organizationRepository: OrganizationRepository,
                              private val bodyRepository: BodyRepository) {

    suspend fun execute(): Transfer<List<BodyOrganization>> =
            when (val bodies = bodyRepository.getBodyList()) {
                is Transfer.Error -> bodies
                is Transfer.Success -> {
                    val bodyOrganizations = ArrayList<BodyOrganization>()
                    bodies.data.data.forEach { body ->
                        val bodyOrganization = getBodyOrganization(body)
                        if (bodyOrganization != null) {
                            bodyOrganizations.add(bodyOrganization)
                        }
                    }
                    Transfer.Success(bodyOrganizations)
                }
            }


    private suspend fun getBodyOrganization(body: Body): BodyOrganization? {
        when (val organizations = organizationRepository.getOrganizationList(body.organization)) {
            is Transfer.Success -> organizations.data.data.forEach { organization: Organization ->
                return BodyOrganizationMapper.map(body, organization)
            }
        }
        return null

    }
}
