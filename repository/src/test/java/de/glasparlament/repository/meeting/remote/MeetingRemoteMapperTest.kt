package de.glasparlament.repository.meeting.remote

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MeetingRemoteMapperTest {

    @Test
    fun test_map_should_work(){

        //when:
        val result = MeetingRemoteMapper.map(MEETING_LIST)

        //then:
        Assertions.assertEquals(1, result.size)
        Assertions.assertEquals(MEETING.id, result[0].id)
        Assertions.assertEquals(MEETING.name, result[0].name)
        Assertions.assertEquals(MEETING.body, result[0].body)
        Assertions.assertEquals(1, result[0].agendaItem.size)
        Assertions.assertEquals(AGENDA_ITEM.id, result[0].agendaItem[0].id)
        Assertions.assertEquals(AGENDA_ITEM.name, result[0].agendaItem[0].name)
        Assertions.assertEquals(AGENDA_ITEM.meeting, result[0].agendaItem[0].meeting)
        Assertions.assertEquals(AGENDA_ITEM.number, result[0].agendaItem[0].number)
        Assertions.assertEquals(1, result[0].agendaItem[0].auxiliaryFile.size)
        Assertions.assertEquals(FILE.id, result[0].agendaItem[0].auxiliaryFile[0].id)
        Assertions.assertEquals(FILE.name, result[0].agendaItem[0].auxiliaryFile[0].name)
        Assertions.assertEquals(FILE.accessUrl, result[0].agendaItem[0].auxiliaryFile[0].accessUrl)
    }

    companion object {

        private val FILE = FileRemote(
                id = "file.id",
                name = "file.name",
                accessUrl = "file.accessUrl"
        )
        private val AGENDA_ITEM = AgendaItemRemote(
                id = "agendaItem.id",
                name = "agendaItem.number",
                number = "agendaItem.name",
                meeting = "agendaItem.meeting",
                auxiliaryFile = listOf(FILE)
        )
        private val MEETING = MeetingRemote(
                id = "meeting.id",
                name = "meeting.name",
                body = "meeting.body",
                agendaItem = listOf(AGENDA_ITEM)
        )
        private val MEETING_LIST = MeetingList(listOf(MEETING))

    }
}