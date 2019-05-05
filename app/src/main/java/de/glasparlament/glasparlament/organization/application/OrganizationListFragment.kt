package de.glasparlament.glasparlament.organization.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import dagger.android.support.DaggerFragment
import de.glasparlament.glasparlament.MainActivity
import de.glasparlament.glasparlament.R
import de.glasparlament.glasparlament.databinding.OrganizationListFragmentBinding
import javax.inject.Inject

class OrganizationListFragment : DaggerFragment() {

    @Inject
    lateinit var factory: OrganizationListViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val viewModel = ViewModelProviders.of(this, factory).get(OrganizationListViewModel::class.java)

        val binding = DataBindingUtil.inflate<OrganizationListFragmentBinding>(
                inflater, R.layout.organization_list_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        (activity as MainActivity).supportActionBar!!.setTitle(R.string.app_name)
        (activity as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        val adapter = OrganizationAdapter()

        binding.organizationList.adapter = adapter
        subscribeUi(adapter, viewModel)

        return binding.root
    }

    private fun subscribeUi(adapter: OrganizationAdapter, viewModel: OrganizationListViewModel) {
        viewModel.organizations.observe(viewLifecycleOwner, Observer { organizations ->
            if (organizations != null) {
                adapter.submitList(organizations)
            }
        })
    }
}
