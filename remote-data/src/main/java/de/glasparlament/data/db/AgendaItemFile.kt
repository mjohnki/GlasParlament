package de.glasparlament.data.db

import androidx.room.Embedded
import androidx.room.Relation

class AgendaItemFile (
        @Embedded
        val agendaItem: AgendaItem,

        @Relation(parentColumn = "agendaItemId", entityColumn = "agendaItem")
        val files: List<File>
)
