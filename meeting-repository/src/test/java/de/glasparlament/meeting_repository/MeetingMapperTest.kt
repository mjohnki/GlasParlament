package de.glasparlament.meeting_repository

import de.glasparlament.data.db.AgendaItem
import de.glasparlament.data.db.Meeting
import de.glasparlament.data.db.MeetingAgendaItem
import org.junit.Assert
import org.junit.Test

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
        Assert.assertEquals(meeting.id, result.id)
        Assert.assertEquals(meeting.body, result.body)
        Assert.assertEquals(meeting.name, result.name)
        Assert.assertEquals(1, result.agendaItem.size)
        Assert.assertEquals(agendaItem.id, result.agendaItem[0])
    }
}