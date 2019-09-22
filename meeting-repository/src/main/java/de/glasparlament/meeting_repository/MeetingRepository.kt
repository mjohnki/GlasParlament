package de.glasparlament.meeting_repository

import de.glasparlament.data.MeetingRemote
import de.glasparlament.data.Transfer
import de.glasparlament.data.db.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

interface MeetingRepository {
    suspend fun getMeetingList(url: String): Transfer<List<Meeting>>
}

class MeetingRepositoryImpl(
        private val api: MeetingApi,
        private val meetingDao: MeetingDao,
        private val agendaItemDao: AgendaItemDao,
        private val fileDao: FileDao) : MeetingRepository {

    override suspend fun getMeetingList(url: String): Transfer<List<Meeting>> =
            when (val remoteData = api.getMeetingList(url)) {
                is Transfer.Error -> remoteData
                is Transfer.Success -> {

                    remoteData.data.data.forEach { meetingRemote ->
                       saveMeeting(meetingRemote)
                    }

                    Transfer.Success(MeetingMapper.map(meetingDao.getMeetings()))
                }
            }

    private suspend fun saveMeeting(meetingRemote: MeetingRemote) {
        if(meetingDao.hasMeeting(meetingRemote.id)) {
            return
        }
        meetingDao.insert(MeetingDbMapper.map(meetingRemote))
        meetingRemote.agendaItem.forEach { agendaItem ->
            agendaItemDao.insert(AgendaItemDbMapper.map(agendaItem))
            fileDao.insert(FileDbMapper.map(agendaItem.auxiliaryFile, agendaItem.id))
        }
    }
}

