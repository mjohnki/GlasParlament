package de.glasparlament.repository.meeting.local

import de.glasparlament.repository.agendaItem.local.AgendaItemDao
import de.glasparlament.repository.meeting.Meeting
import de.glasparlament.repository.meeting.local.file.FileDao
import de.glasparlament.repository.meeting.local.file.FileMapper
import de.glasparlament.repository.meeting.local.meeting.MeetingDao
import de.glasparlament.repository.meeting.local.meeting.MeetingDbMapper

class MeetingLocal(private val meetingDao: MeetingDao,
                   private val agendaItemDao: AgendaItemDao,
                   private val fileDao: FileDao) {

    suspend fun save(meetings: List<Meeting>) {
        meetings.forEach { meetingRemote ->
            saveMeeting(meetingRemote)
        }
    }

    suspend fun get() =
            meetingDao.getMeetings()


    private suspend fun saveMeeting(meeting: Meeting) {
        if (meetingDao.hasMeeting(meeting.id)) {
            return
        }
        meetingDao.insert(MeetingDbMapper.map(meeting))
        meeting.agendaItem.forEach { agendaItem ->
            agendaItemDao.insert(AgendaItemDbMapper.map(agendaItem))
            fileDao.insert(FileMapper.map(agendaItem.auxiliaryFile, agendaItem.id))
        }
    }
}
