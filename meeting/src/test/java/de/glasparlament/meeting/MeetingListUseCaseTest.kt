package de.glasparlament.meeting

import de.glasparlament.data.Transfer
import de.glasparlament.meeting_repository.MeetingRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MeetingListUseCaseTest {

    private val repository = mockk<MeetingRepository>()
    private val useCase  = MeetingListUseCase(repository)

    @Test
    fun testUseCaseWithError() {
        //given:
        val errorMessage = "Error Loading Data"
        val url = "http://test.test"
        val list = Transfer.Error(errorMessage)
        coEvery { repository.getMeetingList(url) } returns list

        //when:
        val result = runBlocking {useCase.execute(url)}

        //then:
        Assert.assertTrue(result is Transfer.Error)
        Assert.assertEquals(errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testUseCaseWithSuccess() {
        //given:
        val url = "http://test.test"
        val meetingList = Transfer.Success(TestData.meetingList)
        coEvery { repository.getMeetingList(url) } returns  meetingList

        //when:
        val result = runBlocking {useCase.execute(url)}

        //then:
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals((result as Transfer.Success).data.size,  1)
    }
}