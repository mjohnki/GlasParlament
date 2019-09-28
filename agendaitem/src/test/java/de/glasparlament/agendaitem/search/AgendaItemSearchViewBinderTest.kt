package de.glasparlament.agendaitem.search

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.agendaItemRepository.AgendaItem
import de.glasparlament.agendaItemRepository.File
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class AgendaItemSearchViewBinderTest {

    private val binder = AgendaItemSearchViewBinder()
    private val adapter = mockk<AgendaItemSearchAdapter>(relaxed = true)
    private val agendaList = mockk<RecyclerView>(relaxed = true)
    private val progressBar = mockk<View>(relaxed = true)
    private val views = AgendaItemSearchViewBinder.Views(agendaList, progressBar)

    @Test
    fun testSuccessWithStateLoaded() {
        //given:
        val model = AgendaItemSearchViewModel.State.Loaded(listOf())
        val params = AgendaItemSearchViewBinder.Params(model, adapter, views)

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
        val model = AgendaItemSearchViewModel.State.Loading
        val params = AgendaItemSearchViewBinder.Params(model, adapter, views)

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
        val model = AgendaItemSearchViewModel.State.Error
        val params = AgendaItemSearchViewBinder.Params(model, adapter, views)

        //when:
        binder(params)

        //then:
        verify { progressBar.visibility = View.VISIBLE }
        verify { agendaList.visibility = View.GONE }
        verify(exactly = 0) { adapter.submitList(any()) }
    }
}
