package de.glasparlament.repository.agendaItem

import de.glasparlament.repository.meeting.local.file.File

data class AgendaItem(
        var id: String,
        var number: String,
        var name: String,
        var meeting: String,
        var auxiliaryFile: List<File>)
