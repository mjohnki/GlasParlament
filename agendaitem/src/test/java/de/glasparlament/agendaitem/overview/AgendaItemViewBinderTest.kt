package de.glasparlament.agendaitem.overview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.agendaItemRepository.AgendaItem
import de.glasparlament.agendaItemRepository.File
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class AgendaItemViewBinderTest {

    private val binder = AgendaItemViewBinder()
    private val adapter = mockk<AgendaItemAdapter>(relaxed = true)
    private val agendaList = mockk<RecyclerView>(relaxed = true)
    private val progressBar = mockk<View>(relaxed = true)
    private val views = AgendaItemViewBinder.Views(agendaList, progressBar)

    @Test
    fun testSuccessWithStateLoaded() {
        //given:
        val agendaItem = AgendaItem(
                id = "id",
                name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
                number = "16",
                meeting = "http://test.test",
                auxiliaryFile = listOf()
        )
        val model = AgendaItemViewModel.State.Loaded(listOf(agendaItem))
        val params = AgendaItemViewBinder.Params(model, adapter, views)

        //when:
        binder(params)

        //then:
        verify { progressBar.visibility = View.GONE }
        verify { agendaList.visibility = View.VISIBLE }
        verify { adapter.submitList(model.agendaItems) }
    }

    @Test
    fun testSuccessWithStateLoading() {
        //given:
        val model = AgendaItemViewModel.State.Loading
        val params = AgendaItemViewBinder.Params(model, adapter, views)

        //when:
        binder(params)

        //then:
        verify { progressBar.visibility = View.VISIBLE }
        verify { agendaList.visibility = View.GONE }
        verify(exactly = 0) { adapter.submitList(any()) }
    }

    @Test
    fun testSuccessWithStateError() {
        //given:
        val model = AgendaItemViewModel.State.Error
        val params = AgendaItemViewBinder.Params(model, adapter, views)

        //when:
        binder(params)

        //then:
        verify { progressBar.visibility = View.VISIBLE }
        verify { agendaList.visibility = View.GONE }
        verify(exactly = 0) { adapter.submitList(any()) }
    }
}
