package de.glasparlament.repository.meeting.remote

data class MeetingRemote(
        var id: String = "",
        var name: String = "",
        var agendaItem: List<AgendaItemRemote> =  mutableListOf(),
        var body: String = "")

data class MeetingList(val data: List<MeetingRemote> = listOf())
