package de.glasparlament.meeting_repository

import de.glasparlament.data.MeetingRemote
import de.glasparlament.data.MeetingList
import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

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
        Assert.assertTrue(result is Transfer.Error)
        Assert.assertEquals(MeetingApi.errorMessageMeetingList, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetMeetingListWithSuccess() {
        //given:
        val url = "http://test.test"
        val response = Response.success(200,  TestData.meetingList)
        coEvery { endpoint.getMeetingList(url) } returns response

        //when:
        val result = runBlocking {api.getMeetingList(url)}

        //then:
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals(TestData.meetingList, (result as Transfer.Success).data)
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
        Assert.assertTrue(result is Transfer.Error)
        Assert.assertEquals(MeetingApi.errorMessageMeeting, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetMeetingWithSuccess() {
        //given:
        val url = "http://test.test"
        val response = Response.success(200,  TestData.meeting)
        coEvery { endpoint.getMeeting(url) } returns response

        //when:
        val result = runBlocking {api.getMeeting(url)}

        //then:
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals(TestData.meeting, (result as Transfer.Success).data)
    }
}