package de.glasparlament.agendaitem.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.glasparlament.agendaItemRepository.AgendaItemSearchResult
import de.glasparlament.agendaitem.R
import de.glasparlament.common.observe
import de.glasparlament.common.observeNavigation
import kotlinx.android.synthetic.main.agenda_item_search_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class AgendaItemSearchFragment : Fragment(), AgendaItemSearchAdapter.OnItemClickListener, TextWatcher {

    private val agendaViewModel: AgendaItemSearchViewModel by viewModel()
    private val adapter = AgendaItemSearchAdapter(this)
    private val binder = AgendaItemSearchViewBinder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar!!.title = "Suche"
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.agenda_item_search_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search.addTextChangedListener(this)
        observe(agendaViewModel.state, ::updateUI)
        observeNavigation(agendaViewModel.navigationCommand, findNavController())
    }

    private fun updateUI(state: AgendaItemSearchViewModel.State) {
        binder(AgendaItemSearchViewBinder.Params(state, adapter,
                AgendaItemSearchViewBinder.Views(agenda_list, progressBar)
        ))
    }

    override fun onItemClick(agendaItem: AgendaItemSearchResult) {
        val direction = AgendaItemSearchFragmentDirections.agendaItemFragment(agendaItem.id)
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
