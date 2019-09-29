package de.glasparlament.organization

import android.view.View
import androidx.recyclerview.widget.RecyclerView

internal class OrganizationViewStateBinder {

    data class Params(
            val state: OrganizationListViewModel.State,
            val adapter: OrganizationAdapter,
            val views: Views
    )

    data class Views(
            val agendaList: RecyclerView,
            val progressBar: View
    )

    operator fun invoke(params: Params) {
        when (params.state) {
            is OrganizationListViewModel.State.Loaded -> {
                params.views.agendaList.visibility = View.VISIBLE
                params.views.progressBar.visibility = View.GONE
                updateAdapter(params.state.meetings, params.adapter, params.views.agendaList)
            }
            is OrganizationListViewModel.State.Error -> {
                params.views.agendaList.visibility = View.GONE
                params.views.progressBar.visibility = View.VISIBLE
            }
            is OrganizationListViewModel.State.Loading -> {
                params.views.agendaList.visibility = View.GONE
                params.views.progressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun updateAdapter(agendaItems: List<BodyOrganization>,
                              adapter: OrganizationAdapter,
                              agendaList: RecyclerView) {
        agendaList.adapter = adapter
        adapter.submitList(agendaItems)
    }
}
