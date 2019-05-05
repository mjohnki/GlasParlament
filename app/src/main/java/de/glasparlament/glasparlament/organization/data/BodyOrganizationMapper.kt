package de.glasparlament.glasparlament.organization.data

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