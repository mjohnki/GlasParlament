package de.glasparlament.meeting

import de.glasparlament.data.Transfer
import de.glasparlament.meeting_repository.Meeting
import de.glasparlament.meeting_repository.MeetingRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

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
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testUseCaseWithSuccess() {
        //given:
        val url = "http://test.test"
        val meeting = Meeting(
                id = "id",
                name = "39. Sitzung des Plenums",
                agendaItem = listOf(),
                body = "http://test.test"
        )
        val meetingList = Transfer.Success(listOf(meeting))
        coEvery { repository.getMeetingList(url) } returns  meetingList

        //when:
        val result = runBlocking {useCase.execute(url)}

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals((result as Transfer.Success).data.size,  1)
    }
}