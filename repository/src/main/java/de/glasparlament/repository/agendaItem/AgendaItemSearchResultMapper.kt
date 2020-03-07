package de.glasparlament.repository.agendaItem

import de.glasparlament.data.db.AgendaItemFile
import de.glasparlament.data.db.Meeting
import de.glasparlament.repository.agendaItem.AgendaItemSearchResult

object AgendaItemSearchResultMapper {

    fun map(agendaItemFile: AgendaItemFile, meeting: Meeting): AgendaItemSearchResult =
            AgendaItemSearchResult(
                    id = agendaItemFile.agendaItem.id,
                    number = agendaItemFile.agendaItem.number,
                    name = agendaItemFile.agendaItem.name,
                    meetingName = meeting.name
            )

}
