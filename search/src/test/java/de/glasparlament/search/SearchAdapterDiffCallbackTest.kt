package de.glasparlament.search

import de.glasparlament.repository.agendaItem.AgendaItemSearchResult
import de.glasparlament.search.ui.DiffCallback
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SearchAdapterDiffCallbackTest {

    private val diff = DiffCallback()

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
}