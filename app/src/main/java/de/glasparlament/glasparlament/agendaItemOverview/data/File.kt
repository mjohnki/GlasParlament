package de.glasparlament.glasparlament.agendaItemOverview.data

import androidx.recyclerview.widget.DiffUtil

class File constructor(
       var id: String,
        var name: String,
        var accessUrl: String) {

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<File> = object : DiffUtil.ItemCallback<File>() {
            override fun areItemsTheSame(oldItem: File, newItem: File): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: File, newItem: File): Boolean {
                return oldItem == newItem
            }
        }
    }
}