package de.glasparlament.glasparlament.agendaItemDetail.domain

import de.glasparlament.glasparlament.agendaItemOverview.data.AgendaItem
import de.glasparlament.glasparlament.meeting.data.Meeting
import de.glasparlament.glasparlament.organization.data.BodyOrganization
import io.reactivex.Observable

interface AgendaItemDetailRepository {
    fun  getAgendaItem(url: String) : Observable<AgendaItem>
}