package de.glasparlament.agendaitem.overview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.agendaItemRepository.AgendaItem

class AgendaItemViewBinder {

    data class Params(
            val state: AgendaItemViewModel.State,
            val adapter: AgendaItemAdapter,
            val views: Views
    )

    data class Views(
            val agendaList: RecyclerView,
            val progressBar: View
    )

    operator fun invoke(params: Params) {
        when (params.state) {
            is AgendaItemViewModel.State.Loaded -> {
                params.views.agendaList.visibility = View.VISIBLE
                params.views.progressBar.visibility = View.GONE
                updateAdapter(params.state.agendaItems, params.adapter, params.views.agendaList)
            }
            is AgendaItemViewModel.State.Error ->{
                params.views.agendaList.visibility = View.GONE
                params.views.progressBar.visibility = View.VISIBLE
            }
            is AgendaItemViewModel.State.Loading ->{
                params.views.agendaList.visibility = View.GONE
                params.views.progressBar.visibility = View.VISIBLE
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
