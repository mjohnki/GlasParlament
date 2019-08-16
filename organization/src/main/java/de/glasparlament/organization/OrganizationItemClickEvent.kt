package de.glasparlament.organization

import androidx.annotation.NonNull
import com.idanatz.oneadapter.external.events.ClickEventHook
import com.idanatz.oneadapter.internal.holders.ViewBinder

class OrganizationItemClickEvent(val viewModel: OrganizationListViewModel) : ClickEventHook<BodyOrganization>() {

    override fun onClick(@NonNull model: BodyOrganization, @NonNull viewBinder: ViewBinder) {
        val direction = OrganizationListFragmentDirections.actionOrganizationListFragmentToMeetingListFragment(model.meeting, model.name)
        viewModel.navigate(direction)
    }
}