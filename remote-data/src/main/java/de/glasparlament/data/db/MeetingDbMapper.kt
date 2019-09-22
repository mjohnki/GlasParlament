package de.glasparlament.data.db

import de.glasparlament.data.MeetingRemote

object MeetingDbMapper {

    fun map(meeting: MeetingRemote): Meeting =
            Meeting(
                    id = meeting.id,
                    name = meeting.name,
                    body = meeting.body
            )

    fun map(meetings: List<MeetingRemote>): List<Meeting> =
            meetings.map { map(it) }

}