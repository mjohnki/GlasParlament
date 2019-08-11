package de.glasparlament.agendaitem.application.overview

import android.annotation.SuppressLint
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
import androidx.navigation.fragment.navArgs
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.events.ClickEventHook
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import dagger.android.support.DaggerFragment
import de.glasparlament.agendaitem.R
import de.glasparlament.agendaitem.databinding.AgendaItemFragmentBinding
import de.glasparlament.common_android.NavigationCommand
import de.glasparlament.data.AgendaItem
import de.glasparlament.data.Meeting
import de.glasparlament.meeting.MeetingListFragmentDirections
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class AgendaItemFragment : DaggerFragment() {

    @Inject
    lateinit var factory: AgendaItemViewModelFactory

    private val oneAdapter: OneAdapter = OneAdapter()

    private lateinit var viewModel: AgendaItemViewModel

    private val args: AgendaItemFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(AgendaItemViewModel::class.java)
        viewModel.bind(args.meetingId)

        (activity as AppCompatActivity).supportActionBar!!.title = args.title
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<AgendaItemFragmentBinding>(
                inflater, R.layout.agenda_item_fragment, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        oneAdapter.attachItemModule(AgendaItemModule()
                .addEventHook(ItemClickEvent(viewModel)))
        oneAdapter.attachTo(binding.agendaList)

        subscribeUi(viewModel)

        return binding.root
    }

    private fun subscribeUi(viewModel: AgendaItemViewModel) {
        viewModel.uiModel.observe(viewLifecycleOwner, Observer { model ->
            oneAdapter.add(model.agendaItems)
        })
        viewModel.navigationCommand.observe(this, Observer { command ->
            when (command) {
                is NavigationCommand.To -> findNavController().navigate(command.directions)
            }
        })
    }
}

private class ItemClickEvent(val viewModel: AgendaItemViewModel) : ClickEventHook<AgendaItem>() {

    override fun onClick(@NonNull model: AgendaItem, @NonNull viewBinder: ViewBinder) {
        val direction = AgendaItemFragmentDirections.actionAgendaFragmentToAgendaItemFragment(model.id)
        viewModel.navigate(direction)
    }
}


private class AgendaItemModule : ItemModule<AgendaItem>() {

    @NotNull
    override fun provideModuleConfig(): ItemModuleConfig {
        return object : ItemModuleConfig() {
            override fun withLayoutResource(): Int {
                return R.layout.agenda_item
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBind(@NotNull model: AgendaItem, @NotNull viewBinder: ViewBinder) {
        val agendaItemNumber: TextView = viewBinder.findViewById(R.id.agendaItemNumber)
        agendaItemNumber.text = "TOP ${model.number}"

        val agendaItemName: TextView = viewBinder.findViewById(R.id.agendaItemName)
        agendaItemName.text = model.name
    }
}
