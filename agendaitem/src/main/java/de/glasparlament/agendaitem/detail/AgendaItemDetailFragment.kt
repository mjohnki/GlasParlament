package de.glasparlament.agendaitem.detail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import de.glasparlament.repository.agendaItem.File
import de.glasparlament.agendaitem.R
import de.glasparlament.common.observe
import kotlinx.android.synthetic.main.agenda_item_detail_fragment.*
import javax.inject.Inject

class AgendaItemDetailFragment : DaggerFragment(), AgendaFileAdapter.OnItemClickListener {

    @Inject
    lateinit var factory: AgendaItemDetailViewModelFactory

    private val args: AgendaItemDetailFragmentArgs by navArgs()
    private val adapter = AgendaFileAdapter(this)
    private val binder = AgendaItemDetailViewBinder()
    private lateinit var viewModel : AgendaItemDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[AgendaItemDetailViewModel::class.java]
        viewModel.bind(args.agendaId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.agenda_item_detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::updateUI)
    }

    override fun onPause() {
        super.onPause()
        file_list.adapter = null
    }

    override fun onResume() {
        super.onResume()
        file_list.adapter = adapter
    }


    override fun onItemClick(file: File) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(file.accessUrl))
    }

    private fun updateUI(state: AgendaItemDetailViewModel.State) {
        binder(AgendaItemDetailViewBinder.Params(state, resources, adapter,
                AgendaItemDetailViewBinder.Views(file_list, number, name, printingheader)
        ))
    }
}
