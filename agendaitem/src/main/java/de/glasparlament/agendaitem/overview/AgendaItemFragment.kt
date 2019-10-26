package de.glasparlament.agendaitem.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import de.glasparlament.agendaItemRepository.AgendaItem
import de.glasparlament.agendaitem.R
import de.glasparlament.common.observe
import kotlinx.android.synthetic.main.agenda_item_fragment.*
import javax.inject.Inject

class AgendaItemFragment : DaggerFragment(), AgendaItemAdapter.OnItemClickListener {

    @Inject
    lateinit var factory: AgendaItemViewModelFactory

    private lateinit var viewModel: AgendaItemViewModel

    private val args: AgendaItemFragmentArgs by navArgs()
    private val adapter = AgendaItemAdapter(this)
    private val binder = AgendaItemViewBinder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(AgendaItemViewModel::class.java)
        viewModel.bind(args.meetingId)
        (activity as AppCompatActivity).supportActionBar!!.title = args.title
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.agenda_item_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::updateUI)
    }

    override fun onPause() {
        super.onPause()
        agenda_list.adapter = null
    }

    override fun onResume() {
        super.onResume()
        agenda_list.adapter = adapter
    }

    private fun updateUI(state: AgendaItemViewModel.State) {
        binder(AgendaItemViewBinder.Params(state, adapter,
                AgendaItemViewBinder.Views(agenda_list, progressBar)
        ))
    }

    override fun onItemClick(agendaItem: AgendaItem) {
        val direction = AgendaItemFragmentDirections.actionAgendaFragmentToAgendaItemFragment(agendaItem.id)
        findNavController().navigate(direction)
    }
}
