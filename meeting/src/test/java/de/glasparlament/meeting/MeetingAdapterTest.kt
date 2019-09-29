package de.glasparlament.meeting

import android.view.View
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import de.glasparlament.meetingRepository.Meeting
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.android.synthetic.main.meeting_list_item.view.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MeetingAdapterTest {

    private val diff = DiffCallback()
    private val view = mockk<View>()
    private val meetingItem = mockk<MaterialCardView>(relaxed = true)
    private val meetingName = mockk<TextView>(relaxed = true)
    private val viewHolder = MeetingViewHolder(view)
    private val listener = mockk<View.OnClickListener>()

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

    @Test
    fun test_bind_works() {
        //given:
        every { view.meetingItem } returns meetingItem
        every { view.meetingName } returns meetingName
        val meeting = Meeting(
                id = "id",
                name = "39. Sitzung des Plenums",
                agendaItem = listOf(),
                body = "http://test.test"
        )

        //when:
        viewHolder.bind(meeting, listener)

        //then:
        verify { meetingName.text = meeting.name }
        verify { meetingItem.setOnClickListener(listener) }

    }
}
