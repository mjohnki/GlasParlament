package de.glasparlament.organization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import de.glasparlament.common_android.NavigationFragment
import de.glasparlament.common_android.NavigationViewModel
import de.glasparlament.organization.databinding.OrganizationListFragmentBinding
import javax.inject.Inject

class OrganizationListFragment : NavigationFragment(), OrganizationAdapter.OnItemClickListener {

    @Inject
    lateinit var factory: OrganizationListViewModelFactory

    lateinit var viewModel: OrganizationListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(OrganizationListViewModel::class.java)
        viewModel.loadData()

        (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.app_name)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val adapter = OrganizationAdapter(this)
        val binding = DataBindingUtil.inflate<OrganizationListFragmentBinding>(
                inflater, R.layout.organization_list_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.organizationList.adapter = adapter

        subscribe(viewModel, adapter)

        return binding.root
    }

    override fun onItemClick(bodyOrganization: BodyOrganization) {
        val direction =
                OrganizationListFragmentDirections.actionOrganizationListFragmentToMeetingListFragment(bodyOrganization.meeting, bodyOrganization.name)
        viewModel.navigate(direction)
    }

    private fun subscribe(viewModel: OrganizationListViewModel, adapter: OrganizationAdapter) {
        viewModel.uiModel.observe(this, Observer { model ->
            adapter.submitList(model.organizations)
        })
    }

    override fun getViewModel(): NavigationViewModel =
            viewModel
}