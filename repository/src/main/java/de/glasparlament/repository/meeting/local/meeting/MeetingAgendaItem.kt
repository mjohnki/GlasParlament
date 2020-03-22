package de.glasparlament.repository.meeting.local.meeting

import androidx.room.Embedded
import androidx.room.Relation
import de.glasparlament.repository.agendaItem.local.AgendaItemDb

class MeetingAgendaItem(

        @Embedded
        val meeting: MeetingDB,

        @Relation(parentColumn = "meetingId", entityColumn = "meeting", entity = AgendaItemDb::class)
        val agendaItems: List<AgendaItemDb>

)
