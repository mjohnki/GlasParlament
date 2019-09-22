package de.glasparlament.agendaitem_repository

import de.glasparlament.data.Transfer
import de.glasparlament.data.db.AgendaItemDao
import de.glasparlament.data.db.MeetingDao

interface AgendaItemRepository {
    suspend fun getAgendaItems(meeting: String): Transfer<List<AgendaItem>>
    suspend fun getAgendaItem(url: String): Transfer<AgendaItem>
    suspend fun searchAgendaItems(search: String): Transfer<List<AgendaItemSearchResult>>
}

class AgendaItemRepositoryImpl(
        private val agendaItemDao: AgendaItemDao,
        private val meetingDao: MeetingDao) : AgendaItemRepository {

    override suspend fun searchAgendaItems(search: String): Transfer<List<AgendaItemSearchResult>> {
        val searchItems = agendaItemDao.searchtAgendaItems("%$search%")
                .map {
                    val meeting = meetingDao.getMeeting(it.agendaItem.meeting)
                    AgendaItemSearchResultMapper.map(it, meeting)
                }
        return Transfer.Success(searchItems)
    }

    override suspend fun getAgendaItems(meeting: String): Transfer<List<AgendaItem>> {
        return Transfer.Success(AgendaItemMapper.map(agendaItemDao.getAgendaItems(meeting)))
    }

    override suspend fun getAgendaItem(url: String): Transfer<AgendaItem> {
        return Transfer.Success(AgendaItemMapper.map(agendaItemDao.getAgendaItem(url)))
    }
}

