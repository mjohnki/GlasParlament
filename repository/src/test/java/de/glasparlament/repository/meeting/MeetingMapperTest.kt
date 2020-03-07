package de.glasparlament.repository.meeting

import de.glasparlament.data.db.AgendaItem
import de.glasparlament.data.db.Meeting
import de.glasparlament.data.db.MeetingAgendaItem
import de.glasparlament.repository.meeting.MeetingMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MeetingMapperTest {

    private val mapper = MeetingMapper

    @Test
    fun test_map_a_single_MeetingAgendaItem(){
        //given:
        val agendaItem = AgendaItem(
                id = "id123",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "17",
                meeting = "http://test.test"
        )
        val meeting = Meeting(
                id = "1",
                name = "39. Sitzung des Plenums",
                body = "http://test.test"
        )
        val data = MeetingAgendaItem(
                meeting = meeting,
                agendaItems = listOf(agendaItem)
        )

        //when:
        val result = mapper.map(data)

        //then:
        Assertions.assertEquals(meeting.id, result.id)
        Assertions.assertEquals(meeting.body, result.body)
        Assertions.assertEquals(meeting.name, result.name)
        Assertions.assertEquals(1, result.agendaItem.size)
        Assertions.assertEquals(agendaItem.id, result.agendaItem[0])
    }
}