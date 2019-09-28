package de.glasparlament.organization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import de.glasparlament.common.NavigationCommand
import de.glasparlament.organization.databinding.OrganizationListFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class OrganizationListFragment : Fragment(), OrganizationAdapter.OnItemClickListener, View.OnClickListener {

    val organizationViewModel: OrganizationListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        organizationViewModel.loadData()

        (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.app_name)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val adapter = OrganizationAdapter(this)
        val binding = DataBindingUtil.inflate<OrganizationListFragmentBinding>(
                inflater, R.layout.organization_list_fragment, container, false)
        binding.viewModel = organizationViewModel
        binding.clickListener = this
        binding.lifecycleOwner = this
        binding.organizationList.adapter = adapter

        subscribe(organizationViewModel, adapter)

        organizationViewModel.navigationCommand.observe(this, Observer { command ->
            command.getContentIfNotHandled()?.let{
                when (it) {
                    is NavigationCommand.To -> findNavController().navigate(it.directions)
                }
            }
        })

        return binding.root
    }

    override fun onItemClick(bodyOrganization: BodyOrganization) {
        val direction =
                OrganizationListFragmentDirections.
                        actionOrganizationListFragmentToMeetingListFragment(
                                bodyOrganization.meeting,
                                bodyOrganization.name)
        organizationViewModel.navigate(direction)
    }

    private fun subscribe(viewModel: OrganizationListViewModel, adapter: OrganizationAdapter) {
        viewModel.uiModel.observe(this, Observer { model ->
            adapter.submitList(model.organizations)
        })
    }

    override fun onClick(v: View?) {
        val direction =
                OrganizationListFragmentDirections.actionAgendaItemSearchFragment()
        organizationViewModel.navigate(direction)
    }
}
