package de.glasparlament.meeting

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.meetingRepository.Meeting

internal class MeetingViewStateBinder {

    data class Params(
            val state: MeetingViewModel.State,
            val adapter: MeetingAdapter,
            val views: Views
    )

    data class Views(
            val agendaList: RecyclerView,
            val progressBar: View
    )

    operator fun invoke(params: Params) {
        when (params.state) {
            is MeetingViewModel.State.Loaded -> {
                params.views.agendaList.visibility = View.VISIBLE
                params.views.progressBar.visibility = View.GONE
                updateAdapter(params.state.meetings, params.adapter, params.views.agendaList)
            }
            is MeetingViewModel.State.Error -> {
                params.views.agendaList.visibility = View.GONE
                params.views.progressBar.visibility = View.VISIBLE
            }
            is MeetingViewModel.State.Loading -> {
                params.views.agendaList.visibility = View.GONE
                params.views.progressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun updateAdapter(agendaItems: List<Meeting>,
                              adapter: MeetingAdapter,
                              agendaList: RecyclerView) {
        agendaList.adapter = adapter
        adapter.submitList(agendaItems)
    }
}
