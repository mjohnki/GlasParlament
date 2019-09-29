package de.glasparlament.agendaitem.detail

import android.view.View
import com.google.android.material.button.MaterialButton
import de.glasparlament.agendaItemRepository.File
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.android.synthetic.main.agenda_file_item.view.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AgendaItemDetailAdapterTest {

    private val diff = DiffCallback()
    private val view = mockk<View>()
    private val filename = mockk<MaterialButton>(relaxed = true)
    private val viewHolder = AgendaFileViewHolder(view)
    private val listener = mockk<View.OnClickListener>()

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

    @Test
    fun test_bind_works() {
        //given:
        every { view.filename } returns filename
        val file = File(
                id = "id",
                name = "filename",
                accessUrl = "url")

        //when:
        viewHolder.bind(file, listener)

        //then:
        verify { filename.setOnClickListener(listener) }
        verify { filename.text = file.name }
    }
}