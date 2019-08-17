package de.glasparlament.organization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.idanatz.oneadapter.OneAdapter
import de.glasparlament.common_android.NavigationFragment
import de.glasparlament.common_android.NavigationViewModel
import de.glasparlament.organization.databinding.OrganizationListFragmentBinding
import javax.inject.Inject

class OrganizationListFragment : NavigationFragment() {

    @Inject
    lateinit var factory: OrganizationListViewModelFactory

    private val oneAdapter: OneAdapter = OneAdapter()

    lateinit var viewModel: OrganizationListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(OrganizationListViewModel::class.java)
        viewModel.loadData()

        (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.app_name)
        //Add back navigation in the title bar
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<OrganizationListFragmentBinding>(
                inflater, R.layout.organization_list_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        oneAdapter.attachItemModule(OrganizationItemModule()
                .addEventHook(OrganizationItemClickEvent(viewModel)))
        oneAdapter.attachTo(binding.organizationList)

        subscribe(viewModel)

        return binding.root
    }

    private fun subscribe(viewModel: OrganizationListViewModel) {
        viewModel.uiModel.observe(this, Observer { model ->
            oneAdapter.add(model.organizations)
        })
    }

    override fun getViewModel(): NavigationViewModel =
            viewModel
}