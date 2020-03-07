package de.glasparlament.repository.meeting

import de.glasparlament.data.MeetingList
import de.glasparlament.data.Transfer
import de.glasparlament.meetingRepository.di.DaggerTestApplicationComponent
import de.glasparlament.repository.meeting.Meeting
import de.glasparlament.repository.meeting.MeetingEndpoint
import de.glasparlament.repository.meeting.MeetingRepository
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import javax.inject.Inject

class ComponentTest {

    @Inject
    lateinit var repository: MeetingRepository

    @Inject
    lateinit var endpoint: MeetingEndpoint

    @BeforeEach
    fun setUp() {
        DaggerTestApplicationComponent.builder()
                .build().inject(this)
    }

    @Test
    fun testGetOrganizationListWithError() {
        //given:
        val url = "http://test.test"
        val errorBody = ResponseBody.create(MediaType.get("text/plain"), "Error")
        val response = Response.error<MeetingList>(400, errorBody)
        coEvery { endpoint.getMeetingList(any()) } returns response

        //when:
        val result = runBlocking { repository.getMeetingList(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(MeetingApi.errorMessageMeetingList, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetMeetingListWithSuccess() {
        //given:
        val url = "http://test.test"
        val response = Response.success(200, MeetingList())
        coEvery { endpoint.getMeetingList(any()) } returns response

        //when:
        val result = runBlocking { repository.getMeetingList(url) }

        //then:
        val expected: List<Meeting> = listOf()
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(expected, (result as Transfer.Success).data)
    }
}
