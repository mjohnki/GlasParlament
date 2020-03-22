package de.glasparlament.repository.body.remote

import de.glasparlament.repository.body.Body

class BodyApi(private val endpoint: BodyEndpoint) {

    suspend fun getBodyList(): List<Body> {
        return endpoint.getBodyList().data
    }
}
