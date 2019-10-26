package de.glasparlament.agendaitem.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import de.glasparlament.agendaItemRepository.AgendaItemSearchResult
import de.glasparlament.agendaitem.R
import de.glasparlament.common.observe
import kotlinx.android.synthetic.main.agenda_item_search_fragment.*
import javax.inject.Inject

class AgendaItemSearchFragment : DaggerFragment(), AgendaItemSearchAdapter.OnItemClickListener, TextWatcher {

    @Inject
    lateinit var factory: AgendaItemSearchViewModelFactory

    private val adapter = AgendaItemSearchAdapter(this)
    private val binder = AgendaItemSearchViewBinder()
    private lateinit var viewModel: AgendaItemSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(AgendaItemSearchViewModel::class.java)

        (activity as AppCompatActivity).supportActionBar!!.title = "Suche"
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.agenda_item_search_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search.addTextChangedListener(this)
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

    private fun updateUI(state: AgendaItemSearchViewModel.State) {
        binder(AgendaItemSearchViewBinder.Params(state, adapter,
                AgendaItemSearchViewBinder.Views(agenda_list, progressBar)
        ))
    }

    override fun onItemClick(agendaItem: AgendaItemSearchResult) {
       val direction = AgendaItemSearchFragmentDirections.agendaItemFragment(agendaItem.id)
        findNavController().navigate(direction)
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
}
