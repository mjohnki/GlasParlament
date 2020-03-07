package de.glasparlament.search.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.repository.agendaItem.AgendaItemSearchResult
import de.glasparlament.search.vm.SearchViewModel

internal class SearchViewBinder {

    data class Params(
            val state: SearchViewModel.State,
            val adapter: SearchAdapter,
            val views: Views
    )

    data class Views(
            val agendaList: RecyclerView,
            val progressBar: View
    )

    operator fun invoke(params: Params) {
        when (params.state) {
            is SearchViewModel.State.Loaded -> {
                params.views.agendaList.visibility = View.VISIBLE
                params.views.progressBar.visibility = View.GONE
                updateAdapter(params.state.agendaItems, params.adapter, params.views.agendaList)
            }
            is SearchViewModel.State.Error ->{
                params.views.agendaList.visibility = View.GONE
                params.views.progressBar.visibility = View.VISIBLE
            }
            is SearchViewModel.State.Loading ->{
                params.views.agendaList.visibility = View.GONE
                params.views.progressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun updateAdapter(agendaItems: List<AgendaItemSearchResult>,
                              adapter: SearchAdapter,
                              agendaList: RecyclerView) {
        agendaList.adapter = adapter
        adapter.submitList(agendaItems)
    }
}
