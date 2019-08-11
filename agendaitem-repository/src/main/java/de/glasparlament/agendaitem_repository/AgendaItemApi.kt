package de.glasparlament.agendaitem_repository

import de.glasparlament.data.AgendaItem
import de.glasparlament.data.Transfer

class AgendaItemApi(private val endpoint: AgendaItemEndpoint)  {

    suspend fun getAgendaItem(url: String): Transfer<AgendaItem> {

        val response = endpoint.getAgendaItem(url)

        if (response.isSuccessful){
            return Transfer.Success(response.body()!!)
        }

        return Transfer.Error(errorMessage)
    }

    companion object {
        const val errorMessage = "Error Fetching AgendaItems"
    }
}