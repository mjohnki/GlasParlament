package de.glasparlament.search

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import de.glasparlament.agendaItemRepository.AgendaItemSearchResult
import de.glasparlament.search.ui.SearchViewHolder
import de.glasparlament.search.ui.DiffCallback
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.android.synthetic.main.search_item.view.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SearchAdapterTest {

    private val diff = DiffCallback()
    private val view = mockk<View>()
    private val resources = mockk<Resources>(relaxed = true)
    private val meetingName = mockk<TextView>(relaxed = true)
    //private val agendaSearch = mockk<MaterialCardView>(relaxed = true)
    private val agendaItemName = mockk<TextView>(relaxed = true)
    private val agendaItemNumber = mockk<TextView>(relaxed = true)
    private val viewHolder = SearchViewHolder(view)
    private val listener = mockk<View.OnClickListener>()

    @Test
    fun test_itemsSame_works_with_same_objects() {
        //given:
        val agendaItem = AgendaItemSearchResult(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meetingName = "meeting")

        //when:
        val result = diff.areItemsTheSame(agendaItem, agendaItem)

        //then:
        assertTrue(result)
    }

    @Test
    fun test_itemsSame_works_with_not_same_objects() {
        //given:
        val agendaItem = AgendaItemSearchResult(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meetingName = "meeting")
        val agendaItemOther = AgendaItemSearchResult(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meetingName = "meeting")

        //when:
        val result = diff.areItemsTheSame(agendaItem, agendaItemOther)

        //then:
        assertFalse(result)
    }

    @Test
    fun test_contentsSame_works_with_same_Objects() {
        //given:
        val agendaItem = AgendaItemSearchResult(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meetingName = "meeting")
        val agendaItemOther = AgendaItemSearchResult(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meetingName = "meeting")

        //when:
        val result = diff.areContentsTheSame(agendaItem, agendaItemOther)

        //then:
        assertTrue(result)
    }

    @Test
    fun test_contentsSame_works_with_different_objects() {
        //given:
        val agendaItem = AgendaItemSearchResult(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meetingName = "meeting")
        val agendaItemOther = AgendaItemSearchResult(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meetingName = "meeting2")

        //when:
        val result = diff.areContentsTheSame(agendaItem, agendaItemOther)

        //then:
        assertFalse(result)
    }

   /* @Test
    fun test_bind_works() {
        //given:
        val top = "TOP: 123 "
        every { resources.getString(any(), any()) } returns top
        every { view.resources } returns resources
        every { view.agendaSearch } returns agendaSearch
        every { view.meetingName } returns meetingName
        every { view.agendaItemName } returns agendaItemName
        every { view.agendaItemNumber } returns agendaItemNumber
        val agendaItem = AgendaItemSearchResult(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meetingName = "meeting")

        //when:
        viewHolder.bind(agendaItem, listener)

        //then:
        verify { resources.getString(any(), agendaItem.number) }
        verify { agendaSearch.setOnClickListener(listener) }
        verify { meetingName.text = agendaItem.meetingName }
        verify { agendaItemName.text = agendaItem.name }
        verify { agendaItemNumber.text = top }
    }*/
}