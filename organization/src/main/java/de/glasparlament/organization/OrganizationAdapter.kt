package de.glasparlament.organization

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.organization_list_item.view.*

internal class OrganizationAdapter(private val listener: OnItemClickListener) :
        ListAdapter<BodyOrganization, OrganizationViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizationViewHolder =
            OrganizationViewHolder.create(parent)

    override fun onBindViewHolder(holder: OrganizationViewHolder, position: Int) {
        val file = getItem(position)
        holder.bind(file, createOnClickListener(file))
    }

    private fun createOnClickListener(bodyOrganization: BodyOrganization): View.OnClickListener =
            View.OnClickListener {
                listener.onItemClick(bodyOrganization)
            }

    interface OnItemClickListener {
        fun onItemClick(bodyOrganization: BodyOrganization)
    }
}

internal class OrganizationViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(bodyOrganization: BodyOrganization, listener: View.OnClickListener) {
        view.organizationName.text = bodyOrganization.name
        view.organizationItem.setOnClickListener(listener)
    }

    companion object {
        fun create(parent: ViewGroup) =
                OrganizationViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.organization_list_item, parent, false))
    }
}

internal class DiffCallback : DiffUtil.ItemCallback<BodyOrganization>() {
    override fun areItemsTheSame(oldItem: BodyOrganization, newItem: BodyOrganization): Boolean =
            oldItem === newItem

    override fun areContentsTheSame(oldItem: BodyOrganization, newItem: BodyOrganization): Boolean =
            oldItem == newItem
}
