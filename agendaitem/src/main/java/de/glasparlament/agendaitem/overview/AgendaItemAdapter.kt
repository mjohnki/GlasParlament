package de.glasparlament.agendaitem.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.agendaitem.databinding.AgendaItemBinding
import de.glasparlament.agendaitem_repository.AgendaItem

class AgendaItemAdapter(private val listener: OnItemClickListener) : ListAdapter<AgendaItem, AgendaItemAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AgendaItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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


    inner class ViewHolder(private val binding: AgendaItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(agendaItem: AgendaItem, listener: View.OnClickListener) {
            binding.agendaItem = agendaItem
            binding.clickListener = listener
            binding.executePendingBindings()
        }
    }

    internal class DiffCallback : DiffUtil.ItemCallback<AgendaItem>() {
        override fun areItemsTheSame(oldItem: AgendaItem, newItem: AgendaItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AgendaItem, newItem: AgendaItem): Boolean {
            return oldItem == newItem
        }
    }
}