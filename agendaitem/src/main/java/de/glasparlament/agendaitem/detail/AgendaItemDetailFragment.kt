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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import de.glasparlament.agendaitem.R
import de.glasparlament.agendaitem.databinding.AgendaItemDetailFragmentBinding
import de.glasparlament.data.File
import javax.inject.Inject

class AgendaItemDetailFragment : DaggerFragment() {

    @Inject
    lateinit var factory: AgendaItemDetailViewModelFactory

    private val args: AgendaItemDetailFragmentArgs by navArgs()

    private lateinit var viewModel : AgendaItemDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this, factory).get(AgendaItemDetailViewModel::class.java)
        viewModel.bind(args.agendaId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<AgendaItemDetailFragmentBinding>(
                inflater, R.layout.agenda_item_detail_fragment, container, false)
        val adapter = AgendaFileAdapter(createOnClickListener(context))

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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

    private fun subscribeUi(adapter: AgendaFileAdapter, viewModel: AgendaItemDetailViewModel) {
        viewModel.uiModel.observe(viewLifecycleOwner, Observer{ model ->
            if (model.agendaItem != null) {
                adapter.submitList(model.agendaItem.auxiliaryFile)
            }
        })
    }

}
