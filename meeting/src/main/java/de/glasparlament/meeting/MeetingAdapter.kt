package de.glasparlament.meeting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.data.Meeting
import de.glasparlament.meeting.databinding.MeetingListItemBinding

class MeetingAdapter : ListAdapter<Meeting, MeetingAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MeetingListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meeting = getItem(position)
        holder.bind(meeting, createOnClickListener(meeting.id, meeting.name))
    }

    private fun createOnClickListener(id: String, title : String): View.OnClickListener {
        return View.OnClickListener { view ->
            //val direction = MeetingListFragmentDirections.actionMeetingListFragmentToAgendaFragment(id, title)
            //Navigation.findNavController(view).navigate(direction)
        }
    }

    inner class ViewHolder(private val binding: MeetingListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Meeting, listener: View.OnClickListener) {
            //binding.meeting = item
            //binding.clickListener = listener
            binding.executePendingBindings()
        }
    }

    internal class DiffCallback : DiffUtil.ItemCallback<Meeting>() {
        override fun areItemsTheSame(oldItem: Meeting, newItem: Meeting): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Meeting, newItem: Meeting): Boolean {
            return oldItem.equals(newItem)
        }
    }
}
