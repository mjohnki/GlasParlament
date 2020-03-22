package de.glasparlament.repository.agendaItem

import de.glasparlament.repository.agendaItem.local.AgendaItemFile
import de.glasparlament.repository.meeting.local.file.FileMapper
import de.glasparlament.repository.meeting.local.meeting.MeetingDB

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

    fun map(agendaItemFile: AgendaItemFile, meeting: MeetingDB): AgendaItemSearchResult =
            AgendaItemSearchResult(
                    id = agendaItemFile.agendaItem.id,
                    number = agendaItemFile.agendaItem.number,
                    name = agendaItemFile.agendaItem.name,
                    meetingName = meeting.name
            )
}
