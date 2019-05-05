package de.glasparlament.glasparlament.agendaItemOverview.application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.glasparlament.agendaItemOverview.data.AgendaItem
import de.glasparlament.glasparlament.databinding.AgendaItemBinding

class AgendaItemAdapter : ListAdapter<AgendaItem, AgendaItemAdapter.ViewHolder>(AgendaItem.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AgendaItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val agendaItem = getItem(position)
        holder.bind(agendaItem, createOnClickListener(agendaItem.id))
    }

    private fun createOnClickListener(id: String): View.OnClickListener {
        return View.OnClickListener { view ->
            val direction = AgendaItemFragmentDirections.actionAgendaFragmentToAgendaItemFragment(id)
            Navigation.findNavController(view).navigate(direction)
        }
    }

    inner class ViewHolder(private val binding: AgendaItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AgendaItem, listener: View.OnClickListener) {
            binding.agendaItem = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }
    }
}
