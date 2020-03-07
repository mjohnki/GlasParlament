package de.glasparlament.agendaitem.detail

import de.glasparlament.repository.agendaItem.AgendaItem
import de.glasparlament.data.Transfer
import de.glasparlament.repository.agendaItem.AgendaItemRepository

class AgendaItemUseCase(private val repository: AgendaItemRepository) {

    suspend fun execute(url: String): Transfer<AgendaItem> {
       return repository.getAgendaItem(url)
    }
}
