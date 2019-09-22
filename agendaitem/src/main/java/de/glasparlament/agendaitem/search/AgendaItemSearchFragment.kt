package de.glasparlament.agendaitem.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import de.glasparlament.agendaitem.R
import de.glasparlament.agendaitem.databinding.AgendaItemSearchFragmentBinding
import de.glasparlament.agendaItemRepository.AgendaItemSearchResult
import de.glasparlament.common.NavigationFragment
import de.glasparlament.common.NavigationViewModel
import javax.inject.Inject

class AgendaItemSearchFragment : NavigationFragment(), AgendaItemSearchAdapter.OnItemClickListener , TextWatcher {

    @Inject
    lateinit var factory: AgendaItemSearchViewModelFactory

    private lateinit var viewModel: AgendaItemSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(AgendaItemSearchViewModel::class.java)

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
        binding.viewModel = viewModel
        binding.agendaList.adapter = adapter

        subscribeUi(viewModel, adapter)

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
        viewModel.navigate(direction)
    }

    override fun afterTextChanged(s: Editable?) {
        //NOOP
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //NOOP
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        s?.let {
            viewModel.search(it.toString())
        }
    }

    override fun getViewModel(): NavigationViewModel =
            viewModel
}
