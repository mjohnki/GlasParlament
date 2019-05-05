package de.glasparlament.glasparlament.agendaItemDetail.data

import de.glasparlament.glasparlament.agendaItemDetail.domain.AgendaItemDetailRepository
import de.glasparlament.glasparlament.agendaItemOverview.data.AgendaItem
import io.reactivex.Observable

class AgendaItemDetailRepositoryImpl(private val api: AgendaItemApi) : AgendaItemDetailRepository {

    override fun getAgendaItem(url: String): Observable<AgendaItem> {
        return api.getAgendaItem(url)
    }
}

