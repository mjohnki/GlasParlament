package de.glasparlament.organization

import de.glasparlament.data.Body
import de.glasparlament.data.Organization

object BodyOrganizationMapper {

    fun map(body: Body, organization: Organization) : BodyOrganization {
        return BodyOrganization(
                organization.id,
                organization.name,
                body.id,
                body.name,
                organization.meeting,
                body.shortname)
    }
}
