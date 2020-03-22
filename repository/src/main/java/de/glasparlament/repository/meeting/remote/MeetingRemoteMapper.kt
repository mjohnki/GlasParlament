package de.glasparlament.repository.meeting.remote

import de.glasparlament.repository.agendaItem.AgendaItem
import de.glasparlament.repository.meeting.local.file.File
import de.glasparlament.repository.meeting.Meeting

object MeetingRemoteMapper {

    fun map(meetings: MeetingList): List<Meeting> =
            meetings.data.map { map(it) }

    private fun map(meeting: MeetingRemote): Meeting =
            Meeting(
                    id = meeting.id,
                    name = meeting.name,
                    body = meeting.body,
                    agendaItem = map(meeting.agendaItem)
            )

    private fun map(agendaItem: AgendaItemRemote): AgendaItem =
            AgendaItem(
                    id = agendaItem.id,
                    number = agendaItem.number,
                    name = agendaItem.name,
                    meeting = agendaItem.meeting,
                    auxiliaryFile = mapFiles(agendaItem.auxiliaryFile)
            )

    private fun map(meetings: List<AgendaItemRemote>): List<AgendaItem> =
            meetings.map { map(it) }

    private fun map(file: FileRemote): File =
            File(
                    id = file.id,
                    name = file.name,
                    accessUrl = file.accessUrl
            )

    private fun mapFiles(files: List<FileRemote>): List<File> =
            files.map { map(it) }

}
