package de.glasparlament.repository.agendaItem.local

import de.glasparlament.repository.agendaItem.AgendaItem
import de.glasparlament.repository.agendaItem.AgendaItemMapper
import de.glasparlament.repository.agendaItem.AgendaItemSearchResult
import de.glasparlament.repository.meeting.local.meeting.MeetingDao

class AgendaItemLocal(
        private val agendaItemDao: AgendaItemDao,
        private val meetingDao: MeetingDao
) {

    suspend fun getAgendaItems(meeting: String): List<AgendaItem> =
            AgendaItemMapper.map(agendaItemDao.getAgendaItems(meeting))

    suspend fun getAgendaItem(url: String): AgendaItem =
            AgendaItemMapper.map(agendaItemDao.getAgendaItem(url))

    suspend fun searchAgendaItems(search: String): List<AgendaItemSearchResult> =
            agendaItemDao.searchtAgendaItems("%$search%")
                    .map {
                        val meeting = meetingDao.getMeeting(it.agendaItem.meeting)
                        AgendaItemMapper.map(it, meeting)
                    }
}
