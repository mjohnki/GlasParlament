package de.glasparlament.glasparlament.agendaItemDetail.application

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import de.glasparlament.glasparlament.MainActivity
import de.glasparlament.glasparlament.R
import de.glasparlament.glasparlament.agendaItemOverview.data.File
import de.glasparlament.glasparlament.databinding.AgendaItemDetailFragmentBinding
import javax.inject.Inject

class AgendaItemDetailFragment : DaggerFragment() {

    @Inject
    lateinit var factory: AgendaItemDetailViewModelFactory

    private val args: AgendaItemDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<AgendaItemDetailFragmentBinding>(
                inflater, R.layout.agenda_item_detail_fragment, container, false)
        val adapter = AgendaFileAdapter(createOnClickListener( context))

        (activity as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val viewModel = ViewModelProviders.of(this, factory).get(AgendaItemViewModel::class.java)
        viewModel.bind(args.agendaId)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.fileList.adapter = adapter
        subscribeUi(adapter, viewModel)

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

    private fun subscribeUi(adapter: AgendaFileAdapter, viewModel: AgendaItemViewModel) {
        Transformations.map(viewModel.agendaItem) { agendaItem -> agendaItem.auxiliaryFile }
                .observe(viewLifecycleOwner, Observer{ files ->
            if (files != null) {
                adapter.submitList(files)
            }
        })
    }

}
