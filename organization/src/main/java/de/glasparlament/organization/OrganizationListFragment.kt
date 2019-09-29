package de.glasparlament.organization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.glasparlament.common.observe
import de.glasparlament.common.observeNavigation
import kotlinx.android.synthetic.main.organization_list_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class OrganizationListFragment : Fragment(), OrganizationAdapter.OnItemClickListener, View.OnClickListener {

    private val organizationViewModel: OrganizationListViewModel by viewModel()
    private val adapter = OrganizationAdapter(this)
    private val binder = OrganizationViewStateBinder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        organizationViewModel.loadData()
        (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.app_name)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.organization_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(organizationViewModel.state, ::updateUI)
        observeNavigation(organizationViewModel.navigationCommand, findNavController())
    }

    override fun onItemClick(bodyOrganization: BodyOrganization) {
        val name = resources.getString(
                R.string.name,
                bodyOrganization.bodyShortname,
                bodyOrganization.organizationName)
        val direction =
                OrganizationListFragmentDirections.actionOrganizationListFragmentToMeetingListFragment(
                        bodyOrganization.meeting,
                        name)
        organizationViewModel.navigate(direction)
    }

    override fun onPause() {
        super.onPause()
        organization_list.adapter = null
    }

    override fun onResume() {
        super.onResume()
        organization_list.adapter = adapter
    }

    private fun updateUI(state: OrganizationListViewModel.State) {
        floating_action_button.setOnClickListener(this)
        binder(OrganizationViewStateBinder.Params(state, adapter,
                OrganizationViewStateBinder.Views(organization_list, progressBar)
        ))
    }

    override fun onClick(v: View?) {
        val direction =
                OrganizationListFragmentDirections.actionAgendaItemSearchFragment()
        organizationViewModel.navigate(direction)
    }
}
