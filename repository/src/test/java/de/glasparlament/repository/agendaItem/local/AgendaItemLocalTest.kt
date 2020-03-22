package de.glasparlament.repository.agendaItem.local

import de.glasparlament.repository.meeting.local.file.FileDb
import de.glasparlament.repository.meeting.local.meeting.MeetingDB
import de.glasparlament.repository.meeting.local.meeting.MeetingDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AgendaItemLocalTest {

    private val agendaItemDao: AgendaItemDao = mockk()
    private val meetingDao: MeetingDao = mockk()
    private val local = AgendaItemLocal(agendaItemDao, meetingDao)

    @BeforeEach
    fun setUp() {
        coEvery { agendaItemDao.getAgendaItem(any()) } returns AGENDA_ITEM_FILE
        coEvery { agendaItemDao.getAgendaItems(any()) } returns listOf(AGENDA_ITEM_FILE)
        coEvery { agendaItemDao.searchtAgendaItems(any()) } returns listOf(AGENDA_ITEM_FILE)
        coEvery { meetingDao.getMeeting(any()) } returns MEETING_DB
    }

    @Test
    fun test_getAgendaItems_should_call_dao() {
        //given:
        val meeting = "meeting"

        //when:
        runBlocking { local.getAgendaItems(meeting) }

        //then:
        coVerify { agendaItemDao.getAgendaItems(meeting) }
    }

    @Test
    fun test_getAgendaItem_should_call_dao() {
        //given:
        val url = "test.test"

        //when:
        runBlocking { local.getAgendaItem(url) }

        //then:
        coVerify { agendaItemDao.getAgendaItem(url) }
    }

    @Test
    fun test_searchAgendaItems_should_work_without_search_results() {
        //given:
        val search = "meeting"
        coEvery { agendaItemDao.searchtAgendaItems(any()) } returns emptyList()

        //when:
        runBlocking { local.searchAgendaItems(search) }

        //then:
        coVerify { agendaItemDao.searchtAgendaItems("%$search%") }
        coVerify(exactly = 0) { meetingDao.getMeeting(any()) }
    }

    @Test
    fun test_searchAgendaItems_should_work_with_search_results() {
        //given:
        val search = "meeting"

        //when:
        runBlocking { local.searchAgendaItems(search) }

        //then:
        coVerify { agendaItemDao.searchtAgendaItems("%$search%") }
        coVerify { meetingDao.getMeeting(any()) }
    }


    companion object {
        private val AGENDA_ITEM_DB = AgendaItemDb(
                id = "agendaitem.id",
                name = "agendaitem.name",
                number = "agendaitem.number",
                meeting = "agendaitem.meeting"

        )
        private val MEETING_DB = MeetingDB(
                id = "meeting.id",
                name = "meeting.name",
                body = "meeting.body"
        )
        private val FILE = FileDb(
                id = "file.id",
                name = "file.name",
                accessUrl = "FILE.URL",
                agendaItem = "file.agendaItem"
        )
        private val AGENDA_ITEM_FILE = AgendaItemFile(
                agendaItem = AGENDA_ITEM_DB,
                files = listOf(FILE)
        )
    }

}