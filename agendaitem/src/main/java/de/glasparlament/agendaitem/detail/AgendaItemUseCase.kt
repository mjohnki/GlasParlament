package de.glasparlament.agendaitem.detail

import com.dropbox.android.external.store4.StoreResponse
import de.glasparlament.repository.agendaItem.AgendaItem
import de.glasparlament.repository.agendaItem.AgendaItemRepository
import kotlinx.coroutines.flow.Flow

class AgendaItemUseCase(private val repository: AgendaItemRepository) {

    suspend fun execute(url: String): Flow<StoreResponse<AgendaItem>> =
            repository.getAgendaItem(url)

}
