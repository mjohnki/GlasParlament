package de.glasparlament.agendaitem.detail

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.glasparlament.agendaItemRepository.AgendaItem
import de.glasparlament.agendaitem.R

internal class AgendaItemDetailViewBinder {

    data class Params(
            val state: AgendaItemDetailViewModel.State,
            val resources: Resources,
            val adapter: AgendaFileAdapter,
            val views: Views
    )

    data class Views(
            val fileList: RecyclerView,
            val number: TextView,
            val name: TextView,
            val printingHeader: TextView
    )

    operator fun invoke(params: Params) {
        when (params.state) {
            is AgendaItemDetailViewModel.State.Loaded ->
                update(
                        params.state.agendaItem,
                        params.adapter,
                        params.resources,
                        params.views
                )
        }
    }

    private fun update(agendaItem: AgendaItem,
                       adapter: AgendaFileAdapter,
                       resources: Resources,
                       views: Views) {
        updateAdapter(agendaItem, adapter, views.fileList)
        updatePrintingHeader(agendaItem, views.printingHeader)
        views.name.text = agendaItem.name
        views.number.text = resources.getString(R.string.top, agendaItem.number)
    }

    private fun updateAdapter(agendaItem: AgendaItem,
                              adapter: AgendaFileAdapter,
                              fileList: RecyclerView) {
        fileList.adapter = adapter
        adapter.submitList(agendaItem.auxiliaryFile)
    }

    private fun updatePrintingHeader(agendaItem: AgendaItem, printingHeader: TextView) {
        printingHeader.visibility = if (agendaItem.auxiliaryFile.isNotEmpty())
            View.VISIBLE else View.GONE
    }
}
