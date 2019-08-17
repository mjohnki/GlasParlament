package de.glasparlament.meeting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.data.Meeting
import de.glasparlament.meeting.databinding.MeetingListItemBinding

class MeetingAdapter(private val listener: OnItemClickListener) : ListAdapter<Meeting, MeetingAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MeetingListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = getItem(position)
        holder.bind(file, createOnClickListener(file))
    }

    private fun createOnClickListener(meeting: Meeting): View.OnClickListener {
        return View.OnClickListener {
            listener.onItemClick(meeting)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(meeting: Meeting)
    }


    inner class ViewHolder(private val binding: MeetingListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(meeting: Meeting, listener: View.OnClickListener) {
            binding.meeting = meeting
            binding.clickListener = listener
            binding.executePendingBindings()
        }
    }

    internal class DiffCallback : DiffUtil.ItemCallback<Meeting>() {
        override fun areItemsTheSame(oldItem: Meeting, newItem: Meeting): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Meeting, newItem: Meeting): Boolean {
            return oldItem == newItem
        }
    }
}