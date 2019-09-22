package de.glasparlament.bodyRepository

import de.glasparlament.data.BodyList
import de.glasparlament.data.Transfer

interface BodyRepository {
    suspend fun getBodyList(): Transfer<BodyList>
}

class BodyRepositoryImpl(private val bodyApi: BodyApi) : BodyRepository {

    override suspend fun getBodyList(): Transfer<BodyList>{
        return bodyApi.getBodyList()
    }
}
