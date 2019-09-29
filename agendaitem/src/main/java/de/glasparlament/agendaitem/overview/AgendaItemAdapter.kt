package de.glasparlament.agendaitem.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.agendaItemRepository.AgendaItem
import de.glasparlament.agendaitem.R
import kotlinx.android.synthetic.main.agenda_item.view.*

internal class AgendaItemAdapter(private val listener: OnItemClickListener) :
        ListAdapter<AgendaItem, AgendaItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            AgendaItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: AgendaItemViewHolder, position: Int) {
        val agendaItem = getItem(position)
        holder.bind(agendaItem, createOnClickListener(agendaItem))
    }

    private fun createOnClickListener(agendaItem: AgendaItem): View.OnClickListener {
        return View.OnClickListener {
            listener.onItemClick(agendaItem)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(agendaItem: AgendaItem)
    }
}

internal class AgendaItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(agendaItem: AgendaItem, listener: View.OnClickListener) {
        view.agendaItemContainer.setOnClickListener(listener)
        view.agendaItemName.text = agendaItem.name
        view.agendaItemNumber.text = view.resources.getString(R.string.top, agendaItem.number)
    }

    companion object {
        fun create(parent: ViewGroup) =
                AgendaItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.agenda_item, parent, false))
    }
}

internal class DiffCallback : DiffUtil.ItemCallback<AgendaItem>() {
    override fun areItemsTheSame(oldItem: AgendaItem, newItem: AgendaItem) =
            oldItem === newItem

    override fun areContentsTheSame(oldItem: AgendaItem, newItem: AgendaItem) =
            oldItem == newItem
}
