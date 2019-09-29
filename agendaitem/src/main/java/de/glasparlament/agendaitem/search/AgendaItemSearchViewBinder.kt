package de.glasparlament.agendaitem.search

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.agendaItemRepository.AgendaItem
import de.glasparlament.agendaItemRepository.AgendaItemSearchResult

internal class AgendaItemSearchViewBinder {

    data class Params(
            val state: AgendaItemSearchViewModel.State,
            val adapter: AgendaItemSearchAdapter,
            val views: Views
    )

    data class Views(
            val agendaList: RecyclerView,
            val progressBar: View
    )

    operator fun invoke(params: Params) {
        when (params.state) {
            is AgendaItemSearchViewModel.State.Loaded -> {
                params.views.agendaList.visibility = View.VISIBLE
                params.views.progressBar.visibility = View.GONE
                updateAdapter(params.state.agendaItems, params.adapter, params.views.agendaList)
            }
            is AgendaItemSearchViewModel.State.Error ->{
                params.views.agendaList.visibility = View.GONE
                params.views.progressBar.visibility = View.VISIBLE
            }
            is AgendaItemSearchViewModel.State.Loading ->{
                params.views.agendaList.visibility = View.GONE
                params.views.progressBar.visibility = View.VISIBLE
            }

        }

    }

    private fun updateAdapter(agendaItems: List<AgendaItemSearchResult>,
                              adapter: AgendaItemSearchAdapter,
                              agendaList: RecyclerView) {
        agendaList.adapter = adapter
        adapter.submitList(agendaItems)
    }
}
