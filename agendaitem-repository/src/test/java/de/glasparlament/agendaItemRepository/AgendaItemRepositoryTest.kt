package de.glasparlament.agendaItemRepository

import de.glasparlament.data.Transfer
import de.glasparlament.data.db.*
import de.glasparlament.data.db.AgendaItem
import de.glasparlament.data.db.File
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AgendaItemRepositoryTest {

    private val agendaItemDao = mockk<AgendaItemDao>()
    private val meetingDao = mockk<MeetingDao>()
    private val repository: AgendaItemRepository = AgendaItemRepositoryImpl(agendaItemDao, meetingDao)

    @Test
    fun test_getAgendaItem_should_work() {
        //given:
        val url = "http://test.test"
        val agendaItem = AgendaItem(
                id = "123",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "14",
                meeting = "http://meeting.test"
        )
        val file = File(
                id = "12",
                name = "new File",
                agendaItem = "http://agendaItem.test",
                accessUrl = "http://access.test"

        )
        val agendaItemFile = AgendaItemFile(
                agendaItem = agendaItem,
                files = listOf(file)
        )
        coEvery { agendaItemDao.getAgendaItem(url) } returns agendaItemFile

        //when:
        val result = runBlocking { repository.getAgendaItem(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals("123", (result as Transfer.Success).data.id)
    }

    @Test
    fun test_getAgendaItems_should_work() {
        //given:
        val url = "http://test.test"
        val agendaItem = AgendaItem(
                id = "123",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "14",
                meeting = "http://meeting.test"
        )
        val file = File(
                id = "12",
                name = "new File",
                agendaItem = "http://agendaItem.test",
                accessUrl = "http://access.test"

        )
        val agendaItemFile = AgendaItemFile(
                agendaItem = agendaItem,
                files = listOf(file)
        )
        coEvery { agendaItemDao.getAgendaItems(url) } returns listOf(agendaItemFile)

        //when:
        val result = runBlocking { repository.getAgendaItems(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(1, (result as Transfer.Success).data.size)
        Assertions.assertEquals("123", result.data[0].id)
    }

    @Test
    fun test_searchAgendaItems_should_work() {
        //given:
        val search = "holz"
        val meeting = Meeting(
                id = "1",
                name = "39. Sitzung des Plenums",
                body = "http://test.test"
        )
        val agendaItem = AgendaItem(
                id = "123",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "14",
                meeting = "http://meeting.test"
        )
        val file = File(
                id = "12",
                name = "new File",
                agendaItem = "http://agendaItem.test",
                accessUrl = "http://access.test"

        )
        val agendaItemFile = AgendaItemFile(
                agendaItem = agendaItem,
                files = listOf(file)
        )
        coEvery { agendaItemDao.searchtAgendaItems("%$search%") } returns listOf(agendaItemFile)
        coEvery { meetingDao.getMeeting(agendaItem.meeting) } returns meeting

        //when:
        val result = runBlocking { repository.searchAgendaItems(search) }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(1, (result as Transfer.Success).data.size)
        Assertions.assertEquals("123", result.data[0].id)
    }
}