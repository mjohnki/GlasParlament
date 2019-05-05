package de.glasparlament.glasparlament.agendaItemOverview.data

import androidx.recyclerview.widget.DiffUtil

class AgendaItem constructor(
        var id: String,
        var number: String,
        var name: String,
        var meeting: String,
        var auxiliaryFile: List<File> ) {

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<AgendaItem> = object : DiffUtil.ItemCallback<AgendaItem>() {
            override fun areItemsTheSame(oldItem: AgendaItem, newItem: AgendaItem): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: AgendaItem, newItem: AgendaItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
