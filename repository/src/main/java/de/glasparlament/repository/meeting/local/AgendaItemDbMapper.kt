package de.glasparlament.repository.meeting.local

import de.glasparlament.repository.agendaItem.AgendaItem
import de.glasparlament.repository.agendaItem.local.AgendaItemDb

object AgendaItemDbMapper {
    fun map(agendaItem: AgendaItem): AgendaItemDb =
            AgendaItemDb(
                    id = agendaItem.id,
                    number = agendaItem.number,
                    name = agendaItem.name,
                    meeting = agendaItem.meeting
            )
}
