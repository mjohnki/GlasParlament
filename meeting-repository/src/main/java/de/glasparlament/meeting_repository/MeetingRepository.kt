package de.glasparlament.meeting_repository

import de.glasparlament.data.Meeting
import de.glasparlament.data.MeetingList
import de.glasparlament.data.Transfer

interface MeetingRepository {
    suspend fun getMeetingList(url: String): Transfer<MeetingList>
    suspend fun getMeeting(url: String): Transfer<Meeting>
}
class MeetingRepositoryImpl(private val api: MeetingApi) : MeetingRepository {

    override suspend fun getMeetingList(url: String): Transfer<MeetingList> {
        return api.getMeetingList(url)
    }

    override suspend fun getMeeting(url: String): Transfer<Meeting> {
        return api.getMeeting(url)
    }
}

