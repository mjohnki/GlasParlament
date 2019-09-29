package de.glasparlament.meeting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.meetingRepository.Meeting
import kotlinx.android.synthetic.main.meeting_list_item.view.*

internal class MeetingAdapter(private val listener: OnItemClickListener) :
        ListAdapter<Meeting, MeetingViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MeetingViewHolder.create(parent)

    override fun onBindViewHolder(holder: MeetingViewHolder, position: Int) {
        val meeting = getItem(position)
        holder.bind(meeting, createOnClickListener(meeting))
    }

    private fun createOnClickListener(meeting: Meeting) =
            View.OnClickListener {
                listener.onItemClick(meeting)
            }

    interface OnItemClickListener {
        fun onItemClick(meeting: Meeting)
    }
}

internal class MeetingViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(meeting: Meeting, listener: View.OnClickListener) {
        view.meetingItem.setOnClickListener(listener)
        view.meetingName.text = meeting.name
    }

    companion object {
        fun create(parent: ViewGroup) =
                MeetingViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.meeting_list_item, parent, false))
    }
}

internal class DiffCallback : DiffUtil.ItemCallback<Meeting>() {
    override fun areItemsTheSame(oldItem: Meeting, newItem: Meeting) =
            oldItem === newItem


    override fun areContentsTheSame(oldItem: Meeting, newItem: Meeting) =
            oldItem == newItem

}
