package de.glasparlament.agendaitem.overview

import android.content.res.Resources
import android.view.View
import de.glasparlament.repository.agendaItem.AgendaItem
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AgendaItemAdapterTest {

    private val diff = DiffCallback()
    private val view = mockk<View>()
    private val resources = mockk<Resources>(relaxed = true)

    @Test
    fun test_itemsSame_works_with_same_objects() {
        //given:
        val agendaItem = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "17",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )

        //when:
        val result = diff.areItemsTheSame(agendaItem, agendaItem)

        //then:
        assertTrue(result)
    }

    @Test
    fun test_itemsSame_works_with_not_same_objects() {
        //given:
        val agendaItem = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "17",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val agendaItemOther = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "18",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )

        //when:
        val result = diff.areItemsTheSame(agendaItem, agendaItemOther)

        //then:
        assertFalse(result)
    }

    @Test
    fun test_contentsSame_works_with_same_Objects() {
        //given:
        val agendaItem = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "17",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val agendaItemOther = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "17",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )

        //when:
        val result = diff.areContentsTheSame(agendaItem, agendaItemOther)

        //then:
        assertTrue(result)
    }

    @Test
    fun test_contentsSame_works_with_different_objects() {
        //given:
        val agendaItem = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "17",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val agendaItemOther = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "18",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )

        //when:
        val result = diff.areContentsTheSame(agendaItem, agendaItemOther)

        //then:
        assertFalse(result)
    }
}