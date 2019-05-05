package de.glasparlament.glasparlament.meeting.data

import androidx.recyclerview.widget.DiffUtil
import de.glasparlament.glasparlament.agendaItemOverview.data.AgendaItem

data class Meeting(
        var id: String = "",
        var name: String = "",
        var agendaItem: List<AgendaItem> =  mutableListOf(),
        var organization: List<String> = mutableListOf(),
        var body: String = ""){

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<Meeting> = object : DiffUtil.ItemCallback<Meeting>() {
            override fun areItemsTheSame(oldItem: Meeting, newItem: Meeting): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: Meeting, newItem: Meeting): Boolean {
                return oldItem == newItem
            }
        }
    }

}
