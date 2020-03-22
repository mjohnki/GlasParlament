package de.glasparlament.repository.meeting.remote

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class MeetingApiTest {

    private val endpoint: MeetingEndpoint = mockk()
    private val api = MeetingApi(endpoint)

    @Test
    fun test_getMeetingList_should_work(){
        //given:
        coEvery { endpoint.getMeetingList(URL) } returns MeetingList()

        // when:
        runBlocking { api.getMeetingList(URL) }

        //then:
        coVerify { endpoint.getMeetingList(URL) }
    }

    companion object {
        private const val URL =  "meeting.list.url"
    }

}