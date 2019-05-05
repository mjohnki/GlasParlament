package de.glasparlament.glasparlament.agendaItemOverview.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import de.glasparlament.glasparlament.MainActivity
import de.glasparlament.glasparlament.R
import de.glasparlament.glasparlament.databinding.AgendaItemFragmentBinding
import javax.inject.Inject


class AgendaItemFragment : DaggerFragment() {

    @Inject
    lateinit var factory: AgendaItemViewModelFactory

    private val args: AgendaItemFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<AgendaItemFragmentBinding>(
                inflater, R.layout.agenda_item_fragment, container, false)
        val adapter = AgendaItemAdapter()

        (activity as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val viewModel = ViewModelProviders.of(this, factory).get(AgendaItemViewModel::class.java)
        viewModel.bind(args.meetingId)

        (activity as MainActivity).supportActionBar!!.title = args.title


        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.agendaList.adapter = adapter
        subscribeUi(adapter, viewModel)

        return binding.root
    }

    private fun subscribeUi(adapter: AgendaItemAdapter, viewModel: AgendaItemViewModel) {
        viewModel.agendaItems.observe(viewLifecycleOwner, Observer{ agendaitem ->
            if (agendaitem != null) {
                adapter.submitList(agendaitem)
            }
        })
    }
}
