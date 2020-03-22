package de.glasparlament.repository.meeting.remote

data class AgendaItemRemote constructor(
        var id: String,
        var number: String,
        var name: String,
        var meeting: String,
        var auxiliaryFile: List<FileRemote> )

