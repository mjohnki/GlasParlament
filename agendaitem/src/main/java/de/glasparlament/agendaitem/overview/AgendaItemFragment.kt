package de.glasparlament.agendaitem.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import de.glasparlament.agendaItemRepository.AgendaItem
import de.glasparlament.agendaitem.R
import de.glasparlament.agendaitem.databinding.AgendaItemFragmentBinding
import de.glasparlament.common.NavigationCommand
import org.koin.android.viewmodel.ext.android.viewModel

class AgendaItemFragment : Fragment(), AgendaItemAdapter.OnItemClickListener {

    val agendaViewModel: AgendaItemViewModel by viewModel()

    private val args: AgendaItemFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        agendaViewModel.bind(args.meetingId)

        (activity as AppCompatActivity).supportActionBar!!.title = args.title
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val adapter = AgendaItemAdapter(this)
        val binding = DataBindingUtil.inflate<AgendaItemFragmentBinding>(
                inflater, R.layout.agenda_item_fragment, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = agendaViewModel
        binding.agendaList.adapter = adapter

        subscribeUi(agendaViewModel, adapter)

        agendaViewModel.navigationCommand.observe(this, Observer { command ->
            command.getContentIfNotHandled()?.let{
                when (it) {
                    is NavigationCommand.To -> findNavController().navigate(it.directions)
                }
            }
        })

        return binding.root
    }

    private fun subscribeUi(viewModel: AgendaItemViewModel, adapter : AgendaItemAdapter) {
        viewModel.uiModel.observe(viewLifecycleOwner, Observer { model ->
            adapter.submitList(model.agendaItems)
        })
    }

    override fun onItemClick(agendaItem: AgendaItem) {
        val direction
                = AgendaItemFragmentDirections.actionAgendaFragmentToAgendaItemFragment(agendaItem.id)
        agendaViewModel.navigate(direction)
    }
}
