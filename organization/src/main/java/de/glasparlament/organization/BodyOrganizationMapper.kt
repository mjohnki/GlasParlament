package de.glasparlament.organization

import de.glasparlament.data.Body
import de.glasparlament.data.Organization

object BodyOrganizationMapper {

    fun map(body: Body, organization: Organization): BodyOrganization =
            BodyOrganization(
                    organizationId = organization.id,
                    organizationName = organization.name,
                    bodyId = body.id,
                    bodyName = body.name,
                    meeting = organization.meeting,
                    bodyShortname = body.shortname)

}
