package de.glasparlament.agendaitem.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.agendaitem.databinding.AgendaItemSearchBinding
import de.glasparlament.agendaItemRepository.AgendaItemSearchResult

class AgendaItemSearchAdapter: ListAdapter<AgendaItemSearchResult, AgendaItemSearchAdapter.ViewHolder>(DiffCallback()) {

    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AgendaItemSearchBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val agendaItem = getItem(position)
        holder.bind(agendaItem, createOnClickListener(agendaItem))
    }

    private fun createOnClickListener(agendaItem: AgendaItemSearchResult): View.OnClickListener {
        return View.OnClickListener {
            listener?.onItemClick(agendaItem)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(agendaItem: AgendaItemSearchResult)
    }


    inner class ViewHolder(private val binding: AgendaItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(agendaItem: AgendaItemSearchResult, listener: View.OnClickListener) {
            binding.agendaItem = agendaItem
            binding.clickListener = listener
            binding.executePendingBindings()
        }
    }

    internal class DiffCallback : DiffUtil.ItemCallback<AgendaItemSearchResult>() {
        override fun areItemsTheSame(oldItem: AgendaItemSearchResult, newItem: AgendaItemSearchResult): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AgendaItemSearchResult, newItem: AgendaItemSearchResult): Boolean {
            return oldItem == newItem
        }
    }
}
