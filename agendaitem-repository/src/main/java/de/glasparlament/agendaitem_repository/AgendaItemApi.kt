package de.glasparlament.agendaitem_repository

import de.glasparlament.data.AgendaItem
import de.glasparlament.data.BaseApi
import de.glasparlament.data.Meeting
import de.glasparlament.data.Transfer

class AgendaItemApi(private val endpoint: AgendaItemEndpoint) : BaseApi() {

    suspend fun getAgendaItem(url: String): Transfer<AgendaItem> {
        return safeApiCall(
                call = {endpoint.getAgendaItem(url)},
                errorMessage = errorMessage
        )
    }

    companion object{
        const val errorMessage = "Error Fetching AgendaItems"
    }
}