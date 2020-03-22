package de.glasparlament.repository.body

import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import kotlinx.coroutines.flow.Flow

interface BodyRepository {
    suspend fun getBodyList(): Flow<StoreResponse<List<Body>>>
}

class BodyRepositoryImpl(private val store: Store<String, List<Body>>) : BodyRepository {

    override suspend fun getBodyList(): Flow<StoreResponse<List<Body>>> =
            store.stream(StoreRequest.cached(key = KEY, refresh = true))

    companion object{
        private const val KEY = "BodyList"
    }
}
