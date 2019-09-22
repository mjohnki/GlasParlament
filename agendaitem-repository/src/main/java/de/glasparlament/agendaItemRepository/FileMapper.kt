package de.glasparlament.agendaItemRepository

import de.glasparlament.data.db.File as FileDb

object FileMapper {

    fun map(file: FileDb): File =
            File(
                    id = file.id,
                    name = file.name,
                    accessUrl = file.accessUrl
            )

    fun map(meetingAgendaItems: List<FileDb>): List<File> =
            meetingAgendaItems.map { map(it) }
}
