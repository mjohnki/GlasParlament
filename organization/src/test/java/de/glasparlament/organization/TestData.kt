package de.glasparlament.organization

import de.glasparlament.data.Body
import de.glasparlament.data.BodyList
import de.glasparlament.data.Organization
import de.glasparlament.data.OrganizationList

object TestData {
    val body = Body(
            id = "id",
            name = "Abgeordnetenhaus von Berlin",
            organization = "http://test.test",
            shortname = "AGH"
    )
    val organization = Organization(
            id = "id",
            name = "Plenum",
            body = "http://test.test",
            meeting = "http://test.test"
    )
    val bodyList = BodyList(listOf(body))
    val organizationList = OrganizationList(listOf(organization))
}