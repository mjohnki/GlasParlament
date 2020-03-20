package de.glasparlament.repository.body

import de.glasparlament.data.BaseApi
import de.glasparlament.data.BodyList
import de.glasparlament.data.Transfer

class BodyApi(private val endpoint: BodyEndpoint) : BaseApi() {

    suspend fun getBodyList(): BodyList {
        return endpoint.getBodyList()
    }

    companion object {
        const val errorMessage = "Error Fetching Body List"
    }
}
