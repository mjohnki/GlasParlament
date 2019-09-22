package de.glasparlament.meeting_repository

data class Meeting(
        var id: String = "",
        var name: String = "",
        var agendaItem: List<String> =  mutableListOf(),
        var body: String = "")