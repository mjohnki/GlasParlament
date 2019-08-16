package de.glasparlament.organization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.events.ClickEventHook
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import dagger.android.support.DaggerFragment
import de.glasparlament.common_android.NavigationCommand
import de.glasparlament.organization.databinding.OrganizationListFragmentBinding
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class OrganizationListFragment : DaggerFragment() {

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
        viewModel.navigationCommand.observe(this, Observer { command ->
            when (command) {
                is NavigationCommand.To -> findNavController().navigate(command.directions)
            }
        })
    }
}

private class OrganizationItemClickEvent(val viewModel: OrganizationListViewModel) : ClickEventHook<BodyOrganization>() {

    override fun onClick(@NonNull model: BodyOrganization, @NonNull viewBinder: ViewBinder) {
        val direction = OrganizationListFragmentDirections.actionOrganizationListFragmentToMeetingListFragment(model.meeting, model.name)
        viewModel.navigate(direction)
    }
}

private class OrganizationItemModule : ItemModule<BodyOrganization>() {

    @NotNull
    override fun provideModuleConfig(): ItemModuleConfig {
        return object : ItemModuleConfig() {
            override fun withLayoutResource(): Int {
                return R.layout.organization_list_item
            }
        }
    }

    override fun onBind(@NotNull model: BodyOrganization, @NotNull viewBinder: ViewBinder) {
        val organizationName: TextView = viewBinder.findViewById(R.id.organizationName)
        organizationName.text = model.name
    }
}
