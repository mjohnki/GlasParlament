package de.glasparlament.repository.agendaItem

import de.glasparlament.data.db.AgendaItemFile

object AgendaItemMapper {

    fun map(agendaItemFile: AgendaItemFile): AgendaItem =
            AgendaItem(
                    id = agendaItemFile.agendaItem.id,
                    number = agendaItemFile.agendaItem.number,
                    name = agendaItemFile.agendaItem.name,
                    meeting = agendaItemFile.agendaItem.meeting,
                    auxiliaryFile = FileMapper.map(agendaItemFile.files)
            )

    fun map(meetingAgendaItems: List<AgendaItemFile>): List<AgendaItem> =
            meetingAgendaItems.map { map(it) }
}
