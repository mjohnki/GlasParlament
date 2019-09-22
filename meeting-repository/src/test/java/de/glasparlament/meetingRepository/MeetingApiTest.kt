package de.glasparlament.meetingRepository

import de.glasparlament.data.MeetingRemote
import de.glasparlament.data.MeetingList
import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import retrofit2.Response
import java.util.ArrayList

class MeetingApiTest {

    private val endpoint = mockk<MeetingEndpoint>()
    private val api = MeetingApi(endpoint)

    @Test
    fun testGetMeetingListWithError() {
        //given:
        val url = "http://test.test"
        val errorBody = ResponseBody.create(MediaType.get("text/plain"), "Error")
        val response = Response.error<MeetingList>(400,  errorBody)
        coEvery { endpoint.getMeetingList(url) } returns  response

        //when:
        val result = runBlocking {api.getMeetingList(url)}

        //then:
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(MeetingApi.errorMessageMeetingList, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetMeetingListWithSuccess() {
        //given:
        val url = "http://test.test"
        val response = Response.success(200,  MeetingList())
        coEvery { endpoint.getMeetingList(url) } returns response

        //when:
        val result = runBlocking {api.getMeetingList(url)}

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(MeetingList(), (result as Transfer.Success).data)
    }

    @Test
    fun testGetMeetingWithError() {
        //given:
        val url = "http://test.test"
        val errorBody = ResponseBody.create(MediaType.get("text/plain"), "Error")
        val response = Response.error<MeetingRemote>(400,  errorBody)
        coEvery { endpoint.getMeeting(url) } returns response

        //when:
        val result = runBlocking {api.getMeeting(url)}

        //then:
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(MeetingApi.errorMessageMeeting, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetMeetingWithSuccess() {
        //given:
        val url = "http://test.test"
        val meeting = MeetingRemote("", "",  ArrayList(), ArrayList(), "")
        val response = Response.success(200,  meeting)
        coEvery { endpoint.getMeeting(url) } returns response

        //when:
        val result = runBlocking {api.getMeeting(url)}

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(meeting, (result as Transfer.Success).data)
    }
}