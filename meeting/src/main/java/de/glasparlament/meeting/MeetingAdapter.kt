package de.glasparlament.meeting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.meetingRepository.Meeting
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.meeting_list_item.*
import kotlinx.android.synthetic.main.meeting_list_item.view.*

class MeetingAdapter :
        ListAdapter<Meeting, MeetingViewHolder>(DiffCallback()) {

    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MeetingViewHolder.create(parent)

    override fun onBindViewHolder(holder: MeetingViewHolder, position: Int) {
        val meeting = getItem(position)
        holder.bind(meeting, createOnClickListener(meeting))
    }

    private fun createOnClickListener(meeting: Meeting) =
            View.OnClickListener {
                listener?.onItemClick(meeting)
            }

    interface OnItemClickListener {
        fun onItemClick(meeting: Meeting)
    }
}

class MeetingViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {

    override val containerView: View =
            itemView

    fun bind(meeting: Meeting, listener: View.OnClickListener) {
        meetingItem.setOnClickListener(listener)
        meetingName.text = meeting.name
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
