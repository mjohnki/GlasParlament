package de.glasparlament.data

data class Meeting(
        var id: String = "",
        var name: String = "",
        var agendaItem: List<AgendaItem> =  mutableListOf(),
        var organization: List<String> = mutableListOf(),
        var body: String = "")