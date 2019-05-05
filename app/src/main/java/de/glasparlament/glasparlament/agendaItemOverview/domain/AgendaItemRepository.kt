package de.glasparlament.glasparlament.agendaItemOverview.domain

import de.glasparlament.glasparlament.agendaItemOverview.data.AgendaItem
import io.reactivex.Observable

interface AgendaItemRepository {
    fun  getAgendaItemList(url: String) : Observable<AgendaItem>
}