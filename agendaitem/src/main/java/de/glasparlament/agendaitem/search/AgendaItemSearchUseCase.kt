package de.glasparlament.agendaitem.search

import de.glasparlament.agendaitem_repository.AgendaItemRepository
import de.glasparlament.agendaitem_repository.AgendaItemSearchResult
import de.glasparlament.data.Transfer

class AgendaItemSearchUseCase(private val repository: AgendaItemRepository) {

    suspend fun execute(search: String): Transfer<List<AgendaItemSearchResult>> {
        return when(val result = repository.searchAgendaItems(search)){
            is Transfer.Success -> Transfer.Success(result.data)
            is Transfer.Error -> result
        }
    }
}