package de.glasparlament.agendaitem.detail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import de.glasparlament.agendaItemRepository.File
import de.glasparlament.agendaitem.R
import de.glasparlament.common.observe
import de.glasparlament.common.observeNavigation
import kotlinx.android.synthetic.main.agenda_item_detail_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class AgendaItemDetailFragment : Fragment(), AgendaFileAdapter.OnItemClickListener {

    private val args: AgendaItemDetailFragmentArgs by navArgs()
    private val agendaViewModel: AgendaItemDetailViewModel by viewModel()
    private val adapter = AgendaFileAdapter(this)
    private val binder = AgendaItemDetailViewBinder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        agendaViewModel.bind(args.agendaId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.agenda_item_detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(agendaViewModel.state, ::updateUI)
        observeNavigation(agendaViewModel.navigationCommand, findNavController())
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
