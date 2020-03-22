package de.glasparlament.repository.agendaItem.local

import androidx.room.Embedded
import androidx.room.Relation
import de.glasparlament.repository.agendaItem.local.AgendaItemDb
import de.glasparlament.repository.meeting.local.file.FileDb

class AgendaItemFile (
        @Embedded
        val agendaItem: AgendaItemDb,

        @Relation(parentColumn = "agendaItemId", entityColumn = "agendaItem")
        val files: List<FileDb>
)
