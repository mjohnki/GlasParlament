package de.glasparlament.repository.meeting.local.file

object FileMapper {

    fun map(files: List<File>, agendaItemId: String): List<FileDb> =
            files.map { map(it, agendaItemId) }

    private fun map(file: File, agendaItemId: String): FileDb =
            FileDb(
                    id = file.id,
                    name = file.name,
                    accessUrl = file.accessUrl,
                    agendaItem = agendaItemId
            )

    private fun map(file: FileDb): File =
            File(
                    id = file.id,
                    name = file.name,
                    accessUrl = file.accessUrl
            )

    fun map(meetingAgendaItems: List<FileDb>): List<File> =
            meetingAgendaItems.map { map(it) }

}
