package de.glasparlament.glasparlament.agendaItemDetail.application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.glasparlament.agendaItemOverview.data.File
import de.glasparlament.glasparlament.databinding.AgendaFileItemBinding

class AgendaFileAdapter(private val listener: OnItemClickListener) : ListAdapter<File, AgendaFileAdapter.ViewHolder>(File.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AgendaFileItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = getItem(position)
        holder.bind(file, createOnClickListener(file))
    }

    private fun createOnClickListener(file: File): View.OnClickListener {
        return View.OnClickListener { view ->
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
}
