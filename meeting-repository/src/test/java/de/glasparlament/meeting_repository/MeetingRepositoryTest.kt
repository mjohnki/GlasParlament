package de.glasparlament.meeting_repository

import de.glasparlament.data.*
import de.glasparlament.data.db.AgendaItemDao
import de.glasparlament.data.db.FileDao
import de.glasparlament.data.db.MeetingDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MeetingRepositoryTest {

    private val meetingApi = mockk<MeetingApi>()
    private val meetingDao = mockk<MeetingDao>(relaxed = true)
    private val agendaItemDao = mockk<AgendaItemDao>(relaxed = true)
    private val fileDao = mockk<FileDao>(relaxed = true)
    private val repository = MeetingRepositoryImpl(meetingApi, meetingDao, agendaItemDao, fileDao)

    @Test
    fun getMeeting_works_when_api_was_successful_and_meeting_is_already_saved(){
        //given:
        val url = "http://test.test"
        val meeting = MeetingRemote(
                id = "123",
                name = "39. Sitzung des Plenums",
                body = "http://test.test"
        )
        val data = Transfer.Success(MeetingList(listOf(meeting)))
        coEvery { meetingApi.getMeetingList(url) } returns data
        coEvery{ meetingDao.hasMeeting(meeting.id) } returns true
        coEvery { meetingDao.getMeetings() } returns listOf()

        //when:
        val result = runBlocking { repository.getMeetingList(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        coVerify(exactly = 0) { meetingDao.insert(any()) }
    }

    @Test
    fun getMeeting_works_when_api_was_successful_and_meeting_is_not_saved(){
        //given:
        val url = "http://test.test"
        val file = FileRemote(
                id = "12",
                name = "new file",
                accessUrl = "http://test.test"
        )
        val agendaItem = AgendaItemRemote(
                id = "1",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                meeting = "http://test.test",
                number = "1",
                auxiliaryFile = listOf(file)
        )
        val meeting = MeetingRemote(
                id = "123",
                name = "39. Sitzung des Plenums",
                body = "http://test.test",
                agendaItem = listOf(agendaItem)
        )
        val data = Transfer.Success(MeetingList(listOf(meeting)))
        coEvery { meetingApi.getMeetingList(url) } returns data
        coEvery{ meetingDao.hasMeeting(meeting.id) } returns false
        coEvery { meetingDao.getMeetings() } returns listOf()

        //when:
        val result = runBlocking { repository.getMeetingList(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        coVerify(exactly = 1) { meetingDao.insert(any()) }
        coVerify(exactly = 1) { agendaItemDao.insert(any()) }
        coVerify(exactly = 1) { fileDao.insert(any()) }
    }

    @Test
    fun getMeetingList_works_when_api_was_not_successful(){
        //given:
        val url = "http://test.test"
        val errorMessage = "Error"
        val data = Transfer.Error(errorMessage)
        coEvery { meetingApi.getMeetingList(url) } returns data

        //when:
        val result = runBlocking { repository.getMeetingList(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(errorMessage, (result as Transfer.Error).exception)
    }
}