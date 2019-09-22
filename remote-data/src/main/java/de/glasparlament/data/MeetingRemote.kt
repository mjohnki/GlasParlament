package de.glasparlament.data

data class MeetingRemote(
        var id: String = "",
        var name: String = "",
        var agendaItem: List<AgendaItemRemote> =  mutableListOf(),
        var organization: List<String> = mutableListOf(),
        var body: String = "")
