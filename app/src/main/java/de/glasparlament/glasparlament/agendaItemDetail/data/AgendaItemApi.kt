package de.glasparlament.glasparlament.agendaItemDetail.data

import de.glasparlament.glasparlament.agendaItemOverview.data.AgendaItem
import io.reactivex.Observable
import retrofit2.Response

class AgendaItemApi(private val endpoint: AgendaItemEndpoint) {

    fun getAgendaItem(url: String): Observable<AgendaItem> {

        return endpoint.getAgendaItem(url)
                .map(this::handleResponse)
                .toObservable()
    }

    private fun handleResponse(response: Response<AgendaItem>): AgendaItem {
        return response.body()!!
    }
}