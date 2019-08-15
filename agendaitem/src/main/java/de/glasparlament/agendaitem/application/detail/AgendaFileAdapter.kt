package de.glasparlament.agendaitem.application.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.data.File
import de.glasparlament.agendaitem.databinding.AgendaFileItemBinding

class AgendaFileAdapter(private val listener: OnItemClickListener) : ListAdapter<File, AgendaFileAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AgendaFileItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = getItem(position)
        holder.bind(file, createOnClickListener(file))
    }

    private fun createOnClickListener(file: File): View.OnClickListener {
        return View.OnClickListener {
            listener.onItemClick(file)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(file: File)
    }


    inner class ViewHolder(private val binding: AgendaFileItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(file: File, listener: View.OnClickListener) {
            binding.file = file
            binding.clickListener = listener
            binding.executePendingBindings()
        }
    }

    internal class DiffCallback : DiffUtil.ItemCallback<File>() {
        override fun areItemsTheSame(oldItem: File, newItem: File): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: File, newItem: File): Boolean {
            return oldItem == newItem
        }
    }
}
