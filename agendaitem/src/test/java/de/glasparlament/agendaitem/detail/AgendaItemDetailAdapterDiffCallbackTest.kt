package de.glasparlament.agendaitem.detail

import de.glasparlament.repository.meeting.local.file.File
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AgendaItemDetailAdapterDiffCallbackTest {

    private val diff = DiffCallback()

    @Test
    fun test_itemsSame_works_with_same_objects() {
        //given:
        val file = File(
                id = "id",
                name = "filename",
                accessUrl = "url")

        //when:
        val result = diff.areItemsTheSame(file, file)

        //then:
        assertTrue(result)
    }

    @Test
    fun test_itemsSame_works_with_not_same_objects() {
        //given:
        val file = File(
                id = "id",
                name = "filename",
                accessUrl = "url")
        val fileOther = File(
                id = "id",
                name = "filename",
                accessUrl = "newUrl")

        //when:
        val result = diff.areItemsTheSame(file, fileOther)

        //then:
        assertFalse(result)
    }

    @Test
    fun test_contentsSame_works_with_same_Objects() {
        //given:
        val file = File(
                id = "id",
                name = "filename",
                accessUrl = "url")
        val fileOther = File(
                id = "id",
                name = "filename",
                accessUrl = "url")

        //when:
        val result = diff.areContentsTheSame(file, fileOther)

        //then:
        assertTrue(result)
    }

    @Test
    fun test_contentsSame_works_with_different_objects() {
        //given:
        val file = File(
                id = "id",
                name = "filename",
                accessUrl = "url")
        val fileOther = File(
                id = "id",
                name = "filename",
                accessUrl = "newUrl")

        //when:
        val result = diff.areContentsTheSame(file, fileOther)

        //then:
        assertFalse(result)
    }
}