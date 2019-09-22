package de.glasparlament.meetingRepository

data class Meeting(
        var id: String = "",
        var name: String = "",
        var agendaItem: List<String> =  mutableListOf(),
        var body: String = "")
