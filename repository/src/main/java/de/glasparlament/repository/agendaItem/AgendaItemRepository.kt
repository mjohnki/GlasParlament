package de.glasparlament.repository.agendaItem

import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import com.dropbox.android.external.store4.get
import de.glasparlament.repository.agendaItem.local.AgendaItemDao
import de.glasparlament.repository.agendaItem.local.AgendaItemLocal
import de.glasparlament.repository.meeting.local.meeting.MeetingDao
import kotlinx.coroutines.flow.Flow

interface AgendaItemRepository {
    suspend fun getAgendaItems(meeting: String): Flow<StoreResponse<List<AgendaItem>>>
    suspend fun getAgendaItem(url: String): Flow<StoreResponse<AgendaItem>>
    suspend fun searchAgendaItems(search: String): Flow<StoreResponse<List<AgendaItemSearchResult>>>
}

class AgendaItemRepositoryImpl(
        private val agendaItemSearchStore : Store<String, List<AgendaItemSearchResult>>,
        private val agendaItemsStore : Store<String, List<AgendaItem>>,
        private val agendaItemStore : Store<String, AgendaItem>) : AgendaItemRepository {

    override suspend fun searchAgendaItems(search: String): Flow<StoreResponse<List<AgendaItemSearchResult>>> =
            agendaItemSearchStore.stream(StoreRequest.cached(key = search, refresh = false))

    override suspend fun getAgendaItems(meeting: String): Flow<StoreResponse<List<AgendaItem>>> =
            agendaItemsStore.stream(StoreRequest.cached(key = meeting, refresh = false))

    override suspend fun getAgendaItem(url: String): Flow<StoreResponse<AgendaItem>> =
            agendaItemStore.stream(StoreRequest.cached(key = url, refresh = false))

}

