package de.glasparlament.repository.meeting

import de.glasparlament.repository.agendaItem.AgendaItem

data class Meeting(
        var id: String = "",
        var name: String = "",
        var agendaItem: List<AgendaItem> =  mutableListOf(),
        var body: String = "")
