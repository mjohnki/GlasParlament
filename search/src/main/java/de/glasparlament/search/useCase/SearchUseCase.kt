package de.glasparlament.search.useCase

import de.glasparlament.repository.agendaItem.AgendaItemSearchResult
import de.glasparlament.data.Transfer
import de.glasparlament.repository.agendaItem.AgendaItemRepository

class SearchUseCase(private val repository: AgendaItemRepository) {

    suspend fun execute(search: String): Transfer<List<AgendaItemSearchResult>> =
            repository.searchAgendaItems(search)
}
