package de.glasparlament.agendaitem_repository

import de.glasparlament.data.AgendaItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface AgendaItemEndpoint {

    @GET
    suspend fun getAgendaItem(@Url url:String): Response<AgendaItem>
}
