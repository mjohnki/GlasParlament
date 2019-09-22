package de.glasparlament.data.db

import androidx.room.Embedded
import androidx.room.Relation

class MeetingAgendaItem(

        @Embedded
        val meeting: Meeting,

        @Relation(parentColumn = "meetingId", entityColumn = "meeting", entity = AgendaItem::class)
        val agendaItems: List<AgendaItem>

)