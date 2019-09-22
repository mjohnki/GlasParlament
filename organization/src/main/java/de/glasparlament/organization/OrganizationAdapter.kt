package de.glasparlament.organization

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.organization.databinding.OrganizationListItemBinding

class OrganizationAdapter(private val listener: OnItemClickListener) :
        ListAdapter<BodyOrganization, OrganizationAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OrganizationListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = getItem(position)
        holder.bind(file, createOnClickListener(file))
    }

    private fun createOnClickListener(bodyOrganization: BodyOrganization): View.OnClickListener {
        return View.OnClickListener {
            listener.onItemClick(bodyOrganization)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(bodyOrganization: BodyOrganization)
    }


    inner class ViewHolder(private val binding: OrganizationListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bodyOrganization: BodyOrganization, listener: View.OnClickListener) {
            binding.bodyOrganization = bodyOrganization
            binding.clickListener = listener
            binding.executePendingBindings()
        }
    }

    internal class DiffCallback : DiffUtil.ItemCallback<BodyOrganization>() {
        override fun areItemsTheSame(oldItem: BodyOrganization, newItem: BodyOrganization): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: BodyOrganization, newItem: BodyOrganization): Boolean {
            return oldItem == newItem
        }
    }
}
