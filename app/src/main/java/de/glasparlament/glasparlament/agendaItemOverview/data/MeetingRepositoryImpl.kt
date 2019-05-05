package de.glasparlament.glasparlament.agendaItemOverview.data

import de.glasparlament.glasparlament.agendaItemOverview.domain.AgendaItemRepository
import de.glasparlament.glasparlament.meeting.data.Meeting
import de.glasparlament.glasparlament.meeting.data.MeetingApi
import io.reactivex.Observable

class AgendaItemRepositoryImpl(private val api: MeetingApi) : AgendaItemRepository {

    override fun getAgendaItemList(url: String): Observable<AgendaItem> {
        return api.getMeeting(url)
                .map { t: Meeting -> t.agendaItem }
                .flatMapIterable { t: List<AgendaItem> -> t }
    }
}

