package de.glasparlament.repository.body.remote

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class BodyApiTest {
    private val endpoint: BodyEndpoint = mockk()
    private val api = BodyApi(endpoint)

    @Test
    fun test_getBodyList_should_work(){
        //given:
        coEvery { endpoint.getBodyList() } returns BodyList()

        // when:
        runBlocking { api.getBodyList() }

        //then:
        coVerify { endpoint.getBodyList() }
    }

}