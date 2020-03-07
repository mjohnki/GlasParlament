package de.glasparlament.agendaitem.detail

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import de.glasparlament.repository.agendaItem.File
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class AgendaFileViewBinderTest {

    private val binder = AgendaFileViewBinder()

    private val listener = mockk<View.OnClickListener>()
    private val filename = mockk<AppCompatButton>(relaxed = true)
    private val views = AgendaFileViewBinder.Views(
            filename = filename
    )

    @Test
    fun test_bind_works() {
        //given:
        val file = File(
                id = "id",
                name = "filename",
                accessUrl = "url")

        //when:
        binder(AgendaFileViewBinder.Params(
                file = file,
                listener = listener,
                views = views
        ))

        //then:
        verify { filename.setOnClickListener(listener) }
        verify { filename.text = file.name }
    }
}