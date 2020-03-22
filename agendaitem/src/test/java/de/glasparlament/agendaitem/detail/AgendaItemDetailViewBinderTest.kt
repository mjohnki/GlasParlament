package de.glasparlament.agendaitem.detail

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.repository.agendaItem.AgendaItem
import de.glasparlament.repository.meeting.local.file.File
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class AgendaItemDetailViewBinderTest {

    private val binder = AgendaItemDetailViewBinder()
    private val resources = mockk<Resources>(relaxed = true)
    private val adapter = mockk<AgendaFileAdapter>(relaxed = true)
    private val fileList = mockk<RecyclerView>(relaxed = true)
    private val number = mockk<TextView>(relaxed = true)
    private val name = mockk<TextView>(relaxed = true)
    private val printingHeader = mockk<TextView>(relaxed = true)
    private val views = AgendaItemDetailViewBinder.Views(fileList, number, name, printingHeader)

    @Test
    fun testSuccessWithoutFile() {
        //given:
        val agendaItem = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val model = AgendaItemDetailViewModel.State.Loaded(agendaItem)
        val params = AgendaItemDetailViewBinder.Params(model, resources, adapter, views)

        //when:
        binder(params)

        //then:
        verify { name.text = agendaItem.name }
        verify { resources.getString(any(), agendaItem.number) }
        verify { printingHeader.visibility = View.GONE }
        verify { adapter.submitList(agendaItem.auxiliaryFile) }
    }

    @Test
    fun testSuccessWithFile() {
        //given:
        val agendaItem = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meeting = "http://test.test",
                auxiliaryFile = listOf(File("id", "name", "url"))
        )
        val model = AgendaItemDetailViewModel.State.Loaded(agendaItem)
        val params = AgendaItemDetailViewBinder.Params(model, resources, adapter, views)

        //when:
        binder(params)

        //then:
        verify { name.text = agendaItem.name }
        verify { resources.getString(any(), agendaItem.number) }
        verify { printingHeader.visibility = View.VISIBLE }
        verify { adapter.submitList(agendaItem.auxiliaryFile) }
    }

    @Test
    fun testSuccessWithStateLoading() {
        //given:
        val model = AgendaItemDetailViewModel.State.Loading
        val params = AgendaItemDetailViewBinder.Params(model, resources, adapter, views)

        //when:
        binder(params)

        //then:
        verify(exactly = 0) { printingHeader.visibility = View.GONE }
    }
}
