package de.glasparlament.glasparlament.organization.application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.glasparlament.organization.data.BodyOrganization
import de.glasparlament.glasparlament.databinding.OrganizationListItemBinding


class OrganizationAdapter : ListAdapter<BodyOrganization, OrganizationAdapter.ViewHolder>(BodyOrganization.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OrganizationListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bodyOrganization = getItem(position)
        holder.bind(bodyOrganization, createOnClickListener(bodyOrganization.meeting, bodyOrganization.shortname))
    }

    private fun createOnClickListener(id: String, title: String): View.OnClickListener {
        return View.OnClickListener { view ->
            val direction = OrganizationListFragmentDirections.actionOrganizationListFragmentToMeetingListFragment(id, title)
            Navigation.findNavController(view).navigate(direction)
        }
    }

    inner class ViewHolder(private val binding: OrganizationListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BodyOrganization, listener: View.OnClickListener) {
            binding.organization = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }
    }
}
