package de.glasparlament.glasparlament.organization.data

import androidx.recyclerview.widget.DiffUtil

class BodyOrganization constructor(
        var organizationId: String,
        var organizationName: String,
        var bodyId: String,
        var bodyName: String,
        var meeting: String,
        var bodyShortname: String){

    val name: String
        get() = "$bodyName · $organizationName"

    val shortname: String
        get() = "$bodyShortname · $organizationName"

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<BodyOrganization> = object : DiffUtil.ItemCallback<BodyOrganization>() {
            override fun areItemsTheSame(oldItem: BodyOrganization, newItem: BodyOrganization): Boolean {
                return oldItem.organizationId === newItem.organizationId
            }

            override fun areContentsTheSame(oldItem: BodyOrganization, newItem: BodyOrganization): Boolean {
                return oldItem == newItem
            }
        }
    }
}
