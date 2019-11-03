package de.glasparlament.meeting

import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.meetingRepository.Meeting
import de.glasparlament.meeting.MeetingViewModel.State

internal class MeetingViewStateBinder {

    data class Params(
            val state: State,
            val searchAction: View.OnClickListener,
            val adapter: MeetingAdapter,
            val views: Views
    )

    data class Views(
            val meetingList: RecyclerView,
            val progressBar: View,
            val searchButton: ImageButton
    )

    operator fun invoke(params: Params) {
        with(params.views) {
            when (params.state) {
                is State.Loaded -> {
                    meetingList.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    searchButton.visibility = View.VISIBLE
                    searchButton.setOnClickListener(params.searchAction)
                    updateAdapter(params.state.meetings, params.adapter, meetingList)
                }
                is State.Error -> {
                    meetingList.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    searchButton.visibility = View.GONE
                }
                is State.Loading -> {
                    meetingList.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    searchButton.visibility = View.GONE
                }
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
