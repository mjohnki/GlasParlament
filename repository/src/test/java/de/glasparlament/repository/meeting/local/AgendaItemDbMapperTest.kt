package de.glasparlament.repository.meeting.local

import de.glasparlament.repository.agendaItem.AgendaItem
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AgendaItemDbMapperTest {

    @Test
    fun test_map_should_work() {
        //given:
        val agendaItem = AgendaItem(
                id = ID,
                name = NAME,
                number = NUMBER,
                meeting = MEETING,
                auxiliaryFile = emptyList()
        )

        //when:
        val result = AgendaItemDbMapper.map(agendaItem)

        //then:
        Assertions.assertEquals(ID, result.id)
        Assertions.assertEquals(NAME, result.name)
        Assertions.assertEquals(NUMBER, result.number)
        Assertions.assertEquals(MEETING, result.meeting)
    }

    companion object {
        private const val ID = "agendaItem.id"
        private const val NUMBER = "agendaItem.number"
        private const val NAME = "agendaItem.name"
        private const val MEETING = "agendaItem.meeting"
    }

}