package de.glasparlament.data.db

import de.glasparlament.data.AgendaItemRemote

object AgendaItemDbMapper {

    fun map(agendaItem: AgendaItemRemote): AgendaItem =
            AgendaItem(
                    id = agendaItem.id,
                    number = agendaItem.number,
                    name = agendaItem.name,
                    meeting = agendaItem.meeting
            )

    fun map(meetings: List<AgendaItemRemote>): List<AgendaItem> =
            meetings.map { map(it) }

}