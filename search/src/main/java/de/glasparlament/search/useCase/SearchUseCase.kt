package de.glasparlament.search.useCase

import de.glasparlament.agendaItemRepository.AgendaItemRepository
import de.glasparlament.agendaItemRepository.AgendaItemSearchResult
import de.glasparlament.data.Transfer

class SearchUseCase(private val repository: AgendaItemRepository) {

    suspend fun execute(search: String): Transfer<List<AgendaItemSearchResult>> =
            repository.searchAgendaItems(search)
}
