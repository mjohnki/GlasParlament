package de.glasparlament.glasparlament.meeting.application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.glasparlament.meeting.data.Meeting
import de.glasparlament.glasparlament.databinding.MeetingListItemBinding

class MeetingAdapter : ListAdapter<Meeting, MeetingAdapter.ViewHolder>(Meeting.DIFF_CALLBACK) {

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
            val direction = MeetingListFragmentDirections.actionMeetingListFragmentToAgendaFragment(id, title)
            Navigation.findNavController(view).navigate(direction)
        }
    }

    inner class ViewHolder(private val binding: MeetingListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Meeting, listener: View.OnClickListener) {
            binding.meeting = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }
    }
}
