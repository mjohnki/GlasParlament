package de.glasparlament.agendaitem.detail

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import de.glasparlament.agendaItemRepository.File
import de.glasparlament.agendaitem.R
import de.glasparlament.agendaitem.databinding.AgendaItemDetailFragmentBinding
import de.glasparlament.common.NavigationCommand
import org.koin.android.viewmodel.ext.android.viewModel

class AgendaItemDetailFragment : Fragment() {

    private val args: AgendaItemDetailFragmentArgs by navArgs()

    val agendaViewModel : AgendaItemDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        agendaViewModel.bind(args.agendaId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<AgendaItemDetailFragmentBinding>(
                inflater, R.layout.agenda_item_detail_fragment, container, false)
        val adapter = AgendaFileAdapter(createOnClickListener(context))

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.lifecycleOwner = this
        binding.viewModel = agendaViewModel
        binding.fileList.adapter = adapter
        subscribeUi(adapter, agendaViewModel)

        agendaViewModel.navigationCommand.observe(this, Observer { command ->
            command.getContentIfNotHandled()?.let{
                when (it) {
                    is NavigationCommand.To -> findNavController().navigate(it.directions)
                }
            }
        })

        return binding.root
    }

    private fun createOnClickListener(context: Context? ): AgendaFileAdapter.OnItemClickListener {
        return object : AgendaFileAdapter.OnItemClickListener {
            override fun onItemClick(file: File) {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(context, Uri.parse(file.accessUrl))
            }
        }
    }

    private fun subscribeUi(adapter: AgendaFileAdapter, viewModel: AgendaItemDetailViewModel) {
        viewModel.uiModel.observe(viewLifecycleOwner, Observer{ model ->
            if (model.agendaItem != null) {
                adapter.submitList(model.agendaItem.auxiliaryFile)
            }
        })
    }
}
