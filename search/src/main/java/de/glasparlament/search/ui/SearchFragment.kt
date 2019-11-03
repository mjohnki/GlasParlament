package de.glasparlament.search.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import de.glasparlament.agendaItemRepository.AgendaItemSearchResult
import de.glasparlament.common.observe
import de.glasparlament.search.R
import de.glasparlament.search.vm.SearchViewModel
import de.glasparlament.search.vm.SearchViewModelFactory
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject

class SearchFragment : DaggerFragment(), SearchAdapter.OnItemClickListener, TextWatcher {

    @Inject
    lateinit var factory: SearchViewModelFactory

    private val adapter = SearchAdapter(this)
    private val binder = SearchViewBinder()
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)

        (activity as AppCompatActivity).supportActionBar!!.title = "Suche"
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.search_fragment, container, false)

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

    private fun updateUI(state: SearchViewModel.State) {
        binder(SearchViewBinder.Params(state, adapter,
                SearchViewBinder.Views(agenda_list, progressBar)
        ))
    }

    override fun onItemClick(agendaItem: AgendaItemSearchResult) {
        findNavController().popBackStack()
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
