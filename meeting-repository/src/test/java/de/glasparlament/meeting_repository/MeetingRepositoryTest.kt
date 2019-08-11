package de.glasparlament.meeting_repository

import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MeetingRepositoryTest {

    private val api = mockk<MeetingApi>()
    private val repository: MeetingRepository = MeetingRepositoryImpl(api)

    @Test
    fun testGetMeetingWithError() {
        //given:
        val url = "http://test.test"
        val errorMessage = "error loading data"
        val transfer = Transfer.Error(errorMessage)
        coEvery { repository.getMeeting(url) } returns transfer

        //when:
        val result = runBlocking { repository.getMeeting(url) }

        //then:
        Assert.assertTrue(result is Transfer.Error)
        Assert.assertEquals(errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetMeetingWithSuccess() {
        //given:
        val url = "http://test.test"
        val transfer = Transfer.Success(TestData.meeting)
        coEvery { repository.getMeeting(url) } returns transfer

        //when:
        val result = runBlocking { repository.getMeeting(url) }

        //then:
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals(TestData.meeting, (result as Transfer.Success).data)
    }

    @Test
    fun testGetMeetingListWithError() {
        //given:
        val url = "http://test.test"
        val errorMessage = "error loading data"
        val transfer = Transfer.Error(errorMessage)
        coEvery { repository.getMeetingList(url) } returns transfer

        //when:
        val result = runBlocking { repository.getMeetingList(url) }

        //then:
        Assert.assertTrue(result is Transfer.Error)
        Assert.assertEquals(errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetMeetingListWithSuccess() {
        //given:
        val url = "http://test.test"
        val transfer = Transfer.Success(TestData.meetingList)
        coEvery { repository.getMeetingList(url) } returns transfer

        //when:
        val result = runBlocking { repository.getMeetingList(url) }

        //then:
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals(TestData.meetingList, (result as Transfer.Success).data)
    }
}