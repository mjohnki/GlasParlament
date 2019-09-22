package de.glasparlament.agendaitem.detail

import de.glasparlament.agendaitem_repository.AgendaItem
import de.glasparlament.agendaitem_repository.AgendaItemRepository
import de.glasparlament.data.Transfer

class AgendaItemUseCase(private val repository: AgendaItemRepository) {

    suspend fun execute(url: String): Transfer<AgendaItem> {
       return repository.getAgendaItem(url)
    }
}