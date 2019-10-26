package de.glasparlament.organization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import de.glasparlament.common.DeepLink
import de.glasparlament.common.observe
import kotlinx.android.synthetic.main.organization_list_fragment.*
import javax.inject.Inject

class OrganizationListFragment : DaggerFragment(), OrganizationAdapter.OnItemClickListener, View.OnClickListener {

    @Inject
    lateinit var factory: OrganizationListViewModelFactory

    private lateinit var viewModel: OrganizationListViewModel
    private val adapter = OrganizationAdapter(this)
    private val binder = OrganizationViewStateBinder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(OrganizationListViewModel::class.java)
        viewModel.loadData()
        (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.app_name)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.organization_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::updateUI)
    }

    override fun onItemClick(bodyOrganization: BodyOrganization) {
        val name = resources.getString(
                R.string.name,
                bodyOrganization.bodyShortname,
                bodyOrganization.organizationName)
        findNavController().navigate(
                DeepLink.meetingList(
                        resources = resources,
                        title = name,
                        meetingListId = bodyOrganization.meeting)
        )
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
        findNavController().navigate(DeepLink.search(resources))
    }
}
