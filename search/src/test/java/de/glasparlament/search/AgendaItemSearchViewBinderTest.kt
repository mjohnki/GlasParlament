package de.glasparlament.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.search.ui.SearchAdapter
import de.glasparlament.search.ui.SearchViewBinder
import de.glasparlament.search.vm.SearchViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class AgendaItemSearchViewBinderTest {

    private val binder = SearchViewBinder()
    private val adapter = mockk<SearchAdapter>(relaxed = true)
    private val agendaList = mockk<RecyclerView>(relaxed = true)
    private val progressBar = mockk<View>(relaxed = true)
    private val views = SearchViewBinder.Views(agendaList, progressBar)

    @Test
    fun testSuccessWithStateLoaded() {
        //given:
        val model = SearchViewModel.State.Loaded(listOf())
        val params = SearchViewBinder.Params(model, adapter, views)

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
        val model = SearchViewModel.State.Loading
        val params = SearchViewBinder.Params(model, adapter, views)

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
        val model = SearchViewModel.State.Error
        val params = SearchViewBinder.Params(model, adapter, views)

        //when:
        binder(params)

        //then:
        verify { progressBar.visibility = View.VISIBLE }
        verify { agendaList.visibility = View.GONE }
        verify(exactly = 0) { adapter.submitList(any()) }
    }
}
