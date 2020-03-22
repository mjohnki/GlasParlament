package de.glasparlament.repository.meeting

import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import kotlinx.coroutines.flow.Flow

interface MeetingRepository {
    suspend fun getMeetingList(url: String): Flow<StoreResponse<List<Meeting>>>
}

class MeetingRepositoryImpl(
        private val store: Store<String, List<Meeting>>
) : MeetingRepository {

    override suspend fun getMeetingList(url: String): Flow<StoreResponse<List<Meeting>>> =
            store.stream(StoreRequest.cached(key = url, refresh = true))
}
