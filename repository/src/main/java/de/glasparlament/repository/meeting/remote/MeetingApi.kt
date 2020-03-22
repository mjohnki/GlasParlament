package de.glasparlament.repository.meeting.remote

import de.glasparlament.repository.meeting.Meeting

class MeetingApi(private val endpoint: MeetingEndpoint) {

    suspend fun getMeetingList(url: String): List<Meeting> =
            MeetingRemoteMapper.map(endpoint.getMeetingList(url))

}
