package de.glasparlament.agendaitem.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import de.glasparlament.agendaItemRepository.AgendaItem
import de.glasparlament.agendaitem.R
import de.glasparlament.agendaitem.detail.AgendaItemDetailViewBinder
import de.glasparlament.common.observe
import de.glasparlament.common.observeNavigation
import kotlinx.android.synthetic.main.agenda_item_detail_fragment.*
import kotlinx.android.synthetic.main.agenda_item_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class AgendaItemFragment : Fragment(), AgendaItemAdapter.OnItemClickListener {

    private val agendaViewModel: AgendaItemViewModel by viewModel()
    private val args: AgendaItemFragmentArgs by navArgs()
    private val adapter = AgendaItemAdapter()
    private val binder = AgendaItemViewBinder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        agendaViewModel.bind(args.meetingId)

        (activity as AppCompatActivity).supportActionBar!!.title = args.title
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.agenda_item_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.listener = this
        observe(agendaViewModel.state, ::updateUI)
        observeNavigation(agendaViewModel.navigationCommand, findNavController())
    }

    private fun updateUI(state: AgendaItemViewModel.State) {
        binder(AgendaItemViewBinder.Params(state, adapter,
                AgendaItemViewBinder.Views(agenda_list, progressBar)
        ))
    }

    override fun onItemClick(agendaItem: AgendaItem) {
        val direction = AgendaItemFragmentDirections.actionAgendaFragmentToAgendaItemFragment(agendaItem.id)
        agendaViewModel.navigate(direction)
    }
}
