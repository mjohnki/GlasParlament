package de.glasparlament.repository.agendaItem

import de.glasparlament.data.db.AgendaItem
import de.glasparlament.data.db.AgendaItemFile
import de.glasparlament.data.db.File
import de.glasparlament.data.db.Meeting
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AgendaItemSearchResultMapperTest {

    private val mapper = de.johnki.demoandroid.repository.agendaItem.AgendaItemSearchResultMapper

    @Test
    fun test_AgendaItemMapper_should_work() {
        //given:
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
        val meeting = Meeting(
                id = "1",
                name = "39. Sitzung des Plenums",
                body = "http://test.test"
        )
        val agendaItemFile = AgendaItemFile(
                agendaItem = agendaItem,
                files = listOf(file)
        )

        //when:
        val result = mapper.map(agendaItemFile, meeting)

        //then:
        Assertions.assertEquals(agendaItem.id, result.id)
        Assertions.assertEquals(agendaItem.name, result.name)
        Assertions.assertEquals(agendaItem.number, result.number)
        Assertions.assertEquals(meeting.name, result.meetingName)
    }

}