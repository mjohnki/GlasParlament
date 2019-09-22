package de.glasparlament.agendaitem.detail

import de.glasparlament.agendaItemRepository.AgendaItem
import de.glasparlament.agendaItemRepository.AgendaItemRepository
import de.glasparlament.data.Transfer

class AgendaItemUseCase(private val repository: AgendaItemRepository) {

    suspend fun execute(url: String): Transfer<AgendaItem> {
       return repository.getAgendaItem(url)
    }
}
