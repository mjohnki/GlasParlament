package de.glasparlament.glasparlament.agendaItemDetail.data

import de.glasparlament.glasparlament.agendaItemOverview.data.AgendaItem
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface AgendaItemEndpoint {

    @GET
    fun getAgendaItem(@Url url:String): Single<Response<AgendaItem>>
}
