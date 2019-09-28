package de.glasparlament.agendaitem.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import de.glasparlament.agendaItemRepository.AgendaItemSearchResult
import de.glasparlament.agendaitem.R
import de.glasparlament.agendaitem.databinding.AgendaItemSearchFragmentBinding
import de.glasparlament.common.NavigationCommand
import org.koin.android.viewmodel.ext.android.viewModel

class AgendaItemSearchFragment : Fragment(), AgendaItemSearchAdapter.OnItemClickListener , TextWatcher {

    val agendaViewModel: AgendaItemSearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar!!.title = "Suche"
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val adapter = AgendaItemSearchAdapter(this)
        val binding = DataBindingUtil.inflate<AgendaItemSearchFragmentBinding>(
                inflater, R.layout.agenda_item_search_fragment, container, false)

        binding.lifecycleOwner = this
        binding.textWatcher = this
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

    private fun subscribeUi(viewModel: AgendaItemSearchViewModel, adapter : AgendaItemSearchAdapter) {
        viewModel.uiModel.observe(viewLifecycleOwner, Observer { model ->
            adapter.submitList(model.agendaItems)
        })
    }

    override fun onItemClick(agendaItem: AgendaItemSearchResult) {
        val direction
                = AgendaItemSearchFragmentDirections.agendaItemFragment(agendaItem.id)
        agendaViewModel.navigate(direction)
    }

    override fun afterTextChanged(s: Editable?) {
        //NOOP
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //NOOP
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        s?.let {
            agendaViewModel.search(it.toString())
        }
    }
}
