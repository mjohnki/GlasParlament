package de.glasparlament.repository.meeting.local.meeting

import de.glasparlament.repository.meeting.Meeting
import de.glasparlament.repository.meeting.local.meeting.MeetingDB

object MeetingDbMapper {

    fun map(meeting: Meeting): MeetingDB =
            MeetingDB(
                    id = meeting.id,
                    name = meeting.name,
                    body = meeting.body
            )


}
