package de.glasparlament.meetingRepository

import de.glasparlament.data.db.MeetingAgendaItem

object MeetingMapper {

    fun map(meetingAgendaItem: MeetingAgendaItem): Meeting =
            Meeting(
                    id = meetingAgendaItem.meeting.id,
                    name = meetingAgendaItem.meeting.name,
                    agendaItem = meetingAgendaItem.agendaItems.map { it.id },
                    body = meetingAgendaItem.meeting.body
            )

    fun map(meetingAgendaItems: List<MeetingAgendaItem>): List<Meeting> =
            meetingAgendaItems.map { map(it) }
}
