package de.glasparlament.meeting

import de.glasparlament.repository.meeting.Meeting
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MeetingAdapterDiffCallbackTest {

    private val diff = DiffCallback()

    @Test
    fun test_itemsSame_works_with_same_objects() {
        //given:
        val meeting = Meeting(
                id = "id",
                name = "39. Sitzung des Plenums",
                agendaItem = listOf(),
                body = "http://test.test"
        )

        //when:
        val result = diff.areItemsTheSame(meeting, meeting)

        //then:
        assertTrue(result)
    }

    @Test
    fun test_itemsSame_works_with_not_same_objects() {
        //given:
        val meeting = Meeting(
                id = "id",
                name = "39. Sitzung des Plenums",
                agendaItem = listOf(),
                body = "http://test.test"
        )
        val meeting2 = Meeting(
                id = "id",
                name = "40. Sitzung des Plenums",
                agendaItem = listOf(),
                body = "http://test.test"
        )

        //when:
        val result = diff.areItemsTheSame(meeting, meeting2)

        //then:
        assertFalse(result)
    }

    @Test
    fun test_contentsSame_works_with_same_Objects() {
        //given:
        val meeting = Meeting(
                id = "id",
                name = "39. Sitzung des Plenums",
                agendaItem = listOf(),
                body = "http://test.test"
        )
        val meeting2 = Meeting(
                id = "id",
                name = "39. Sitzung des Plenums",
                agendaItem = listOf(),
                body = "http://test.test"
        )

        //when:
        val result = diff.areContentsTheSame(meeting, meeting2)

        //then:
        assertTrue(result)
    }

    @Test
    fun test_contentsSame_works_with_different_objects() {
        //given:
        val meeting = Meeting(
                id = "id",
                name = "39. Sitzung des Plenums",
                agendaItem = listOf(),
                body = "http://test.test"
        )
        val meetingOther = Meeting(
                id = "id",
                name = "40. Sitzung des Plenums",
                agendaItem = listOf(),
                body = "http://test.test"
        )

        //when:
        val result = diff.areContentsTheSame(meeting, meetingOther)

        //then:
        assertFalse(result)
    }
}
