package de.glasparlament.repository.meeting.local

import de.glasparlament.repository.agendaItem.AgendaItem
import de.glasparlament.repository.agendaItem.local.AgendaItemDao
import de.glasparlament.repository.meeting.Meeting
import de.glasparlament.repository.meeting.local.file.File
import de.glasparlament.repository.meeting.local.file.FileDao
import de.glasparlament.repository.meeting.local.meeting.MeetingAgendaItem
import de.glasparlament.repository.meeting.local.meeting.MeetingDB
import de.glasparlament.repository.meeting.local.meeting.MeetingDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MeetingLocalTest {

    private val meetingDao: MeetingDao = mockk(relaxUnitFun = true)
    private val agendaItemDao: AgendaItemDao = mockk(relaxUnitFun = true)
    private val fileDao: FileDao = mockk(relaxUnitFun = true)
    private val local = MeetingLocal(meetingDao, agendaItemDao, fileDao)

    @BeforeEach
    fun setUp() {
        coEvery { meetingDao.hasMeeting(any()) } returns false
    }

    @Test
    fun test_save_should_work_with_empty_list() {

        //when:
        runBlocking { local.save(emptyList()) }

        //then:
        coVerify(exactly = 0) { meetingDao.hasMeeting(any()) }
        coVerify(exactly = 0) { meetingDao.insert(any()) }
        coVerify(exactly = 0) { agendaItemDao.insert(any()) }
        coVerify(exactly = 0) { fileDao.insert(any()) }
    }

    @Test
    fun test_save_should_work() {

        //when:
        runBlocking { local.save(listOf(MEETING)) }

        //then:
        coVerify(exactly = 1) { meetingDao.hasMeeting(any()) }
        coVerify(exactly = 1) { meetingDao.insert(any()) }
        coVerify(exactly = 1) { agendaItemDao.insert(any()) }
        coVerify(exactly = 1) { fileDao.insert(any()) }
    }

    @Test
    fun test_save_should_work_without_agenda_item() {

        //when:
        runBlocking { local.save(listOf(MEETING_WITHOUT_AGENDA_ITEM)) }

        //then:
        coVerify(exactly = 1) { meetingDao.hasMeeting(any()) }
        coVerify(exactly = 1) { meetingDao.insert(any()) }
        coVerify(exactly = 0) { agendaItemDao.insert(any()) }
        coVerify(exactly = 0) { fileDao.insert(any()) }
    }

    @Test
    fun test_save_should_do_nothing_when_meeting_is_already_saved() {
        //given:
        coEvery { meetingDao.hasMeeting(MEETING_WITHOUT_AGENDA_ITEM.id) } returns true

        //when:
        runBlocking { local.save(listOf(MEETING_WITHOUT_AGENDA_ITEM)) }

        //then:
        coVerify(exactly = 1) { meetingDao.hasMeeting(any()) }
        coVerify(exactly = 0) { meetingDao.insert(any()) }
        coVerify(exactly = 0) { agendaItemDao.insert(any()) }
        coVerify(exactly = 0) { fileDao.insert(any()) }
    }

    @Test
    fun test_get_should_work() {
        //given:
        coEvery { meetingDao.getMeetings() } returns listOf(MEETING_AGENDA_ITEM)

        //when:
        runBlocking { local.get() }

        //then:
        coVerify { meetingDao.getMeetings() }
    }

    companion object {
        private val FILE = File(
                id = "file.id",
                name = "file.name",
                accessUrl = "file.accessUrl"
        )
        private val AGENDA_ITEM = AgendaItem(
                id = "agendaItem.id",
                name = "agendaItem.number",
                number = "agendaItem.name",
                meeting = "agendaItem.meeting",
                auxiliaryFile = listOf(FILE)
        )
        private val MEETING = Meeting(
                id = "meeting.id",
                name = "meeting.name",
                body = "meeting.body",
                agendaItem = listOf(AGENDA_ITEM)
        )
        private val MEETING_WITHOUT_AGENDA_ITEM = Meeting(
                id = "meeting.id",
                name = "meeting.name",
                body = "meeting.body",
                agendaItem = emptyList()
        )
        private val MEETING_DB = MeetingDB(
                id = "meeting.id",
                name = "meeting.name",
                body = "meeting.body"
        )
        private val MEETING_AGENDA_ITEM = MeetingAgendaItem(
                meeting = MEETING_DB,
                agendaItems = emptyList()
        )
    }

}