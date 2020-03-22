package de.glasparlament.repository.meeting.local

import de.glasparlament.repository.meeting.Meeting
import de.glasparlament.repository.meeting.local.meeting.MeetingDbMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MeetingDbMapperTest {

    @Test
    fun test_map_should_work() {
        //given:
        val meeting = Meeting(
                id = ID,
                name = NAME,
                body = BODY
        )

        //when:
        val result = MeetingDbMapper.map(meeting)

        //then:
        assertEquals(ID, result.id)
    }

    companion object{
        private const val ID = "meeting.id"
        private const val NAME = "meeting.name"
        private const val BODY = "meeting.body"
    }

}