package de.glasparlament.repository.agendaItem

import de.glasparlament.repository.agendaItem.local.AgendaItemDb
import de.glasparlament.repository.agendaItem.local.AgendaItemFile
import de.glasparlament.repository.meeting.local.file.FileDb
import de.glasparlament.repository.meeting.local.meeting.MeetingDB
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AgendaItemMapperTest {

    @Test
    fun map_should_map_a_AgendaItemFile_to_AgendaItem() {
        //when:
        val result = AgendaItemMapper.map(AGENDA_ITEM_FILE)

        //then:
        Assertions.assertEquals(AGENDA_ITEM_FILE.agendaItem.id, result.id)
        Assertions.assertEquals(AGENDA_ITEM_FILE.agendaItem.name, result.name)
        Assertions.assertEquals(AGENDA_ITEM_FILE.agendaItem.number, result.number)
        Assertions.assertEquals(AGENDA_ITEM_FILE.agendaItem.meeting, result.meeting)
    }

    @Test
    fun map_should_map_a_list_of_AgendaItemFile_to_a_list_of_AgendaItem() {
        //when:
        val result = AgendaItemMapper.map(listOf(AGENDA_ITEM_FILE))

        //then:
        Assertions.assertEquals(1, result.size)
        Assertions.assertEquals(AGENDA_ITEM_FILE.agendaItem.id, result[0].id)
        Assertions.assertEquals(AGENDA_ITEM_FILE.agendaItem.name, result[0].name)
        Assertions.assertEquals(AGENDA_ITEM_FILE.agendaItem.number, result[0].number)
        Assertions.assertEquals(AGENDA_ITEM_FILE.agendaItem.meeting, result[0].meeting)
    }

    @Test
    fun map_should_map_a_AgendaItemFile_and_MeetingDB_to_AgendaItemSearchResult() {
        //when:
        val result = AgendaItemMapper.map(AGENDA_ITEM_FILE, MEETING_DB)

        //then:
        Assertions.assertEquals(AGENDA_ITEM_DB.id, result.id)
        Assertions.assertEquals(AGENDA_ITEM_DB.name, result.name)
        Assertions.assertEquals(AGENDA_ITEM_DB.number, result.number)
        Assertions.assertEquals(MEETING_DB.name, result.meetingName)
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