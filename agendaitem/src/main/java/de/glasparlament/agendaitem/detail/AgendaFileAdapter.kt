package de.glasparlament.agendaitem.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.agendaItemRepository.File
import de.glasparlament.agendaitem.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.agenda_file_item.*

internal class AgendaFileAdapter(private val listener: OnItemClickListener) :
        ListAdapter<File, AgendaFileViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            AgendaFileViewHolder.create(parent)


    override fun onBindViewHolder(holder: AgendaFileViewHolder, position: Int) {
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
}

internal class AgendaFileViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {

    override val containerView: View =
            itemView

    private val binder = AgendaFileViewBinder()

    fun bind(file: File, listener: View.OnClickListener) {
        binder(AgendaFileViewBinder.Params(
                file = file,
                listener = listener,
                views = AgendaFileViewBinder.Views(
                        filename = filename
                )
        ))
    }

    companion object {
        fun create(parent: ViewGroup) =
                AgendaFileViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.agenda_file_item, parent, false))
    }
}

internal class DiffCallback : DiffUtil.ItemCallback<File>() {
    override fun areItemsTheSame(oldItem: File, newItem: File) =
            oldItem === newItem

    override fun areContentsTheSame(oldItem: File, newItem: File) =
            oldItem == newItem
}
