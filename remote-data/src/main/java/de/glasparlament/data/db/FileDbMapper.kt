package de.glasparlament.data.db

import de.glasparlament.data.FileRemote

object FileDbMapper {

    fun map(file: FileRemote, agendaItem: String): File =
            File(
                    id = file.id,
                    name = file.name,
                    accessUrl = file.accessUrl,
                    agendaItem = agendaItem
            )

    fun map(files: List<FileRemote>, agendaItem: String): List<File> =
            files.map { map(it, agendaItem) }

}