package de.glasparlament.glasparlament.organization.domain

import de.glasparlament.glasparlament.organization.data.BodyOrganization
import io.reactivex.Observable

interface OrganizationRepository {
    fun  getBodyOrganizationList() : Observable<BodyOrganization>
}