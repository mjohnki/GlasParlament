package de.glasparlament.agendaitem.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.glasparlament.agendaitem.R
import de.glasparlament.agendaitem.databinding.AgendaItemFragmentBinding
import de.glasparlament.agendaItemRepository.AgendaItem
import de.glasparlament.common.NavigationFragment
import de.glasparlament.common.NavigationViewModel
import javax.inject.Inject

class AgendaItemFragment : NavigationFragment(), AgendaItemAdapter.OnItemClickListener {

    @Inject
    lateinit var factory: AgendaItemViewModelFactory

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
        val adapter = AgendaItemAdapter(this)
        val binding = DataBindingUtil.inflate<AgendaItemFragmentBinding>(
                inflater, R.layout.agenda_item_fragment, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.agendaList.adapter = adapter

        subscribeUi(viewModel, adapter)

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
        viewModel.navigate(direction)
    }

    override fun getViewModel(): NavigationViewModel =
            viewModel
}
