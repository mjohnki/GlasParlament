package de.glasparlament.search.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.agendaItemRepository.AgendaItemSearchResult
import de.glasparlament.search.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.search_item.*
import kotlinx.android.synthetic.main.search_item.view.*

internal class SearchAdapter(private val listener: OnItemClickListener) :
        ListAdapter<AgendaItemSearchResult, SearchViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            SearchViewHolder.create(parent)

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val agendaItem = getItem(position)
        holder.bind(agendaItem, createOnClickListener(agendaItem))
    }

    private fun createOnClickListener(agendaItem: AgendaItemSearchResult): View.OnClickListener {
        return View.OnClickListener {
            listener.onItemClick(agendaItem)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(agendaItem: AgendaItemSearchResult)
    }
}

internal class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {

    override val containerView: View =
            itemView

    fun bind(agendaItem: AgendaItemSearchResult, listener: View.OnClickListener) {
        agendaSearch.setOnClickListener(listener)
        meetingName.text = agendaItem.meetingName
        agendaItemName.text = agendaItem.name
        agendaItemNumber.text = itemView.resources.getString(R.string.top, agendaItem.number)
    }

    companion object {
        fun create(parent: ViewGroup) =
                SearchViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.search_item, parent, false))
    }
}

internal class DiffCallback : DiffUtil.ItemCallback<AgendaItemSearchResult>() {
    override fun areItemsTheSame(oldItem: AgendaItemSearchResult, newItem: AgendaItemSearchResult) =
            oldItem === newItem

    override fun areContentsTheSame(oldItem: AgendaItemSearchResult, newItem: AgendaItemSearchResult) =
            oldItem == newItem
}
