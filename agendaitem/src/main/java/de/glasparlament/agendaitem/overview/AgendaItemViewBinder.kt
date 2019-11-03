package de.glasparlament.agendaitem.overview

import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.agendaItemRepository.AgendaItem
import de.glasparlament.agendaitem.overview.AgendaItemViewModel.State

internal class AgendaItemViewBinder {

    data class Params(
            val state: State,
            val searchAction: View.OnClickListener,
            val adapter: AgendaItemAdapter,
            val views: Views
    )

    data class Views(
            val agendaList: RecyclerView,
            val progressBar: View,
            val searchButton: ImageButton
    )

    operator fun invoke(params: Params) {
        with(params.views) {
            when (params.state) {
                is State.Loaded -> {
                    agendaList.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    searchButton.visibility = View.VISIBLE
                    searchButton.setOnClickListener(params.searchAction)
                    updateAdapter(params.state.agendaItems, params.adapter, agendaList)
                }
                is State.Error -> {
                    agendaList.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    searchButton.visibility = View.GONE
                }
                is State.Loading -> {
                    agendaList.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    searchButton.visibility = View.GONE
                }
            }
        }
    }

    private fun updateAdapter(agendaItems: List<AgendaItem>,
                              adapter: AgendaItemAdapter,
                              agendaList: RecyclerView) {
        agendaList.adapter = adapter
        adapter.submitList(agendaItems)
    }
}
