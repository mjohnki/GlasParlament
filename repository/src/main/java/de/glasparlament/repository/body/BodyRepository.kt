package de.glasparlament.repository.body

import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import de.glasparlament.data.BodyList
import kotlinx.coroutines.flow.Flow

interface BodyRepository {
    suspend fun getBodyList(): Flow<StoreResponse<BodyList>>
}

class BodyRepositoryImpl(private val store: Store<String, BodyList>) : BodyRepository {

    override suspend fun getBodyList(): Flow<StoreResponse<BodyList>> =
            store.stream(StoreRequest.cached(key = "123", refresh = true))
}
