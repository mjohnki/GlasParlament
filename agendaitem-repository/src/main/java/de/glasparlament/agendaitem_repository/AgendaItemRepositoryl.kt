package de.glasparlament.agendaitem_repository

import de.glasparlament.data.AgendaItem
import de.glasparlament.data.Transfer

interface AgendaItemRepository {
    suspend fun getAgendaItem(url: String): Transfer<AgendaItem>
}
class AgendaItemRepositoryImpl(private val agendaItemApi: AgendaItemApi) : AgendaItemRepository {

    override suspend fun getAgendaItem(url: String): Transfer<AgendaItem> {
        return agendaItemApi.getAgendaItem(url)
    }
}

