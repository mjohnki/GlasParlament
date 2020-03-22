package de.glasparlament.search.useCase

import com.dropbox.android.external.store4.StoreResponse
import de.glasparlament.repository.agendaItem.AgendaItemSearchResult
import de.glasparlament.repository.agendaItem.AgendaItemRepository
import kotlinx.coroutines.flow.Flow

class SearchUseCase(private val repository: AgendaItemRepository) {

    suspend fun execute(search: String): Flow<StoreResponse<List<AgendaItemSearchResult>>> =
            repository.searchAgendaItems(search)
}
